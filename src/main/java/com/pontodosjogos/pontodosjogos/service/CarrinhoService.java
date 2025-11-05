package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.controller.request.ItemCarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.CarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.entity.ItemCarrinho;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import com.pontodosjogos.pontodosjogos.exception.RecursoNaoEncontradoException;
import com.pontodosjogos.pontodosjogos.mapper.CarrinhoMapper;
import com.pontodosjogos.pontodosjogos.mapper.ItemCarrinhoMapper;
import com.pontodosjogos.pontodosjogos.repository.CarrinhoRepository;
import com.pontodosjogos.pontodosjogos.repository.ItemCarrinhoRepository;
import com.pontodosjogos.pontodosjogos.repository.ProdutoRepository;
import com.pontodosjogos.pontodosjogos.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final ItemCarrinhoMapper itemCarrinhoMapper;
    private final CarrinhoMapper carrinhoMapper;
    private final UsuarioRepository usuarioRepository;
    // private final UsuarioService usuarioService; // Assumindo que você tem um serviço para buscar o usuário

    // =================================================================
    // MÉTODOS AUXILIARES (LÓGICA CORE)
    // =================================================================

    /**
     * Busca o carrinho ATIVO do usuário logado, ou cria um novo se não existir.
     * @param usuarioId O ID do usuário (obtido via JWT no Controller/Contexto de Segurança).
     * @return O Carrinho ativo.
     */
    public Carrinho getOrCreateActiveCart(Long usuarioId) {
        // Na prática, você precisaria de um método no CarrinhoRepository:
        Optional<Carrinho> carrinhoOptional = carrinhoRepository.findByUsuarioIdAndStatusCarrinho(usuarioId, StatusCarrinho.ABERTO);

        if (carrinhoOptional.isPresent()) {
            return carrinhoOptional.get();
        } else {
            // Log: Criando novo carrinho para o usuário X...
            // **IMPORTANTE:** Você precisará buscar a entidade Usuario real aqui
            // Usuario usuario = usuarioService.findById(usuarioId);
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));

            return carrinhoRepository.save(
                    Carrinho.builder()
                            .dataCriacao(java.time.LocalDateTime.now())
                            .statusCarrinho(StatusCarrinho.ABERTO)
                            .usuario(usuario) // MOCK: substitua por Usuario real
                            .itens(new java.util.ArrayList<>())
                            .build()
            );
        }
    }

    // =================================================================
    // MÉTODOS PRINCIPAIS (CRUD/AÇÕES)
    // =================================================================

    /**
     * Adiciona um novo item ou atualiza a quantidade de um item existente no carrinho.
     * @param usuarioId O ID do usuário.
     * @param request O DTO com o produtoId e a quantidade.
     * @return O DTO de resposta do carrinho atualizado.
     */
    @Transactional
    public CarrinhoResponse adicionarItemAoCarrinho(Long usuarioId, ItemCarrinhoRequest request) {
        Carrinho carrinho = getOrCreateActiveCart(usuarioId);

        // 1. Validar e Buscar o Produto
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado."));

        // **Lógica de Estoque (a ser implementada): Verificar se há estoque suficiente**

        // 2. Tentar encontrar ItemCarrinho existente
        Optional<ItemCarrinho> itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(request.produtoId()))
                .findFirst();

        if (itemExistente.isPresent()) {
            // 3. Atualizar quantidade
            ItemCarrinho item = itemExistente.get();
            item.setQuantidade(item.getQuantidade() + request.quantidade());
            itemCarrinhoRepository.save(item);
        } else {
            // 4. Criar novo ItemCarrinho usando o Mapper
            ItemCarrinho novoItem = itemCarrinhoMapper.toEntity(request, carrinho, produto);
            // O precoUnitarioNaCompra é definido dentro do Mapper (pegando o preço atual do produto)

            // Adicionar à lista do carrinho (essencial para o Hibernate/JPA)
            carrinho.getItens().add(novoItem);
            itemCarrinhoRepository.save(novoItem);
        }

        // Retorna a resposta completa do carrinho
        return getCarrinhoResponse(carrinho);
    }

    /**
     * Remove um item específico do carrinho.
     */
    @Transactional
    public void removerItemDoCarrinho(Long usuarioId, Long itemCarrinhoId) {
        Carrinho carrinho = getOrCreateActiveCart(usuarioId);

        ItemCarrinho itemParaRemover = itemCarrinhoRepository.findById(itemCarrinhoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Item não encontrado no carrinho."));

        // Garantir que o item pertence ao carrinho do usuário
        if (!itemParaRemover.getCarrinho().getId().equals(carrinho.getId())) {
            throw new SecurityException("Acesso negado. O item não pertence ao carrinho do usuário.");
        }

        carrinho.getItens().remove(itemParaRemover); // Remove da lista (se usarmos orphanRemoval=true no Carrinho)
        itemCarrinhoRepository.delete(itemParaRemover); // Deleta do banco
    }

    /**
     * Obtém a visualização completa do carrinho.
     */
    @Transactional(readOnly = true)
    public CarrinhoResponse getCarrinhoAtivoResponse(Long usuarioId) {
        Carrinho carrinho = getOrCreateActiveCart(usuarioId);

        // Mapeia para o DTO de resposta (o Service calcula o total)
        return getCarrinhoResponse(carrinho);
    }

    // =================================================================
    // LÓGICA DE CÁLCULO E MAPEAMENTO DE RESPOSTA
    // =================================================================

    // Método privado para calcular o total e fazer o mapeamento final.
    private CarrinhoResponse getCarrinhoResponse(Carrinho carrinho) {
        BigDecimal total = carrinho.getItens().stream()
                .map(item -> item.getPrecoUnitarioNaCompra().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CarrinhoResponse response = carrinhoMapper.toFullResponseDTO(carrinho);

        // Este é um hack, pois o MapStruct não é ideal para campos calculados.
        // O ideal é usar um @AfterMapping ou o método completo no mapper, mas para
        // simplicidade, fazemos a cópia aqui (assumindo que o DTO é mutável, ou
        // recriado com o valor correto).
        // Se o DTO for um Record (imutável), você precisará de um método
        // 'withTotalCarrinho' ou criar o Record novamente no mapper.
        // response.setTotalCarrinho(total); // Exemplo para um DTO com Setters

        return response; // Se o mapper cuidar do total, ou se o DTO for mutável.
    }



}
