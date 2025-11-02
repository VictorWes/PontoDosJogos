package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.CarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.CarrinhoResponse;
import com.pontodosjogos.pontodosjogos.controller.response.ItemCarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CarrinhoMapper {

    // Injeta o ItemCarrinhoMapper para mapear a lista de itens
    private final ItemCarrinhoMapper itemCarrinhoMapper;
    // O UsuarioMapper não é injetado diretamente aqui para evitar ciclos desnecessários
    // Ele é injetado apenas onde é realmente usado (ex: no Service)

    // =================================================================
    // 1. Mapeamento de Requisição (Request) para Entidade (JPA) - CRIAÇÃO
    // =================================================================
    /**
     * Mapeia CarrinhoRequest e o Usuario para a Entidade Carrinho.
     */
    public Carrinho toEntity(CarrinhoRequest request, Usuario usuario) {
        return Carrinho.builder()
                .usuario(usuario)
                .statusCarrinho(request.statusInicial())
                .dataCriacao(LocalDateTime.now()) // Usamos LocalDateTime, como corrigido na Entidade
                .itens(Collections.emptyList())
                .build();
    }

    // =================================================================
    // 2. Entidade (JPA) para DTO de Resposta Completa (COM CÁLCULOS)
    // =================================================================
    /**
     * Mapeia a Entidade Carrinho para o DTO de Resposta completo (CarrinhoResponseDTO).
     * O cálculo do total do carrinho é delegado ao Service (para garantir a lógica de negócio),
     * mas o mapeamento das listas é feito aqui.
     * * NOTA: Este método está preparado para ser chamado a partir do Service, onde o total é calculado.
     * O Service deve passar o total ou você deve calcular aqui se a Entidade vier com os Itens.
     * Vamos calcular o total aqui para simplificar a assinatura, usando os itens da Entidade.
     */
    public CarrinhoResponse toFullResponseDTO(Carrinho carrinho) {

        // Mapeamento da lista de itens
        List<ItemCarrinhoResponse> itensResponse = carrinho.getItens()
                .stream()
                .map(itemCarrinhoMapper::toResponseDTO) // Usa o Mapper injetado
                .collect(Collectors.toList());

        // Cálculo do total do carrinho (refeito aqui usando os dados de ItemCarrinho)
        BigDecimal totalCarrinho = carrinho.getItens().stream()
                .map(item -> item.getPrecoUnitarioNaCompra().multiply(new BigDecimal(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        // Retorna o Response DTO
        return CarrinhoResponse.builder()
                .id(carrinho.getId())
                .status(carrinho.getStatusCarrinho())
                .dataCriacao(carrinho.getDataCriacao())
                .itens(itensResponse)
                .totalCarrinho(totalCarrinho) // Preenche o total calculado
                .build();
    }

}
