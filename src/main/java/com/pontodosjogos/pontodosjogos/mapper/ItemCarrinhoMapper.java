package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.ItemCarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ItemCarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.entity.ItemCarrinho;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ItemCarrinhoMapper {

    public  ItemCarrinho toEntity(ItemCarrinhoRequest request, Carrinho carrinho, Produto produto) {

        // Simula o mapeamento do MapStruct
        return ItemCarrinho.builder()
                .carrinho(carrinho)               // source = "carrinho"
                .produto(produto)                 // source = "produto"
                .quantidade(request.quantidade()) // source = "request.quantidade"
                // O CRUCIAL: Mapeia o preço atual do Produto (SNAPSHOT)
                .precoUnitarioNaCompra(produto.getPreco()) // source = "produto.preco"
                // O ID é ignorado
                .build();
    }


    // =================================================================
    // 2. Entidade (JPA) para DTO de Resposta (Response) - VISUALIZAÇÃO
    // Equivalente a: ItemCarrinhoResponse toResponseDTO(ItemCarrinho itemCarrinho)
    // =================================================================
    /**
     * Mapeia a Entidade ItemCarrinho para o DTO de Resposta, incluindo o subtotal calculado.
     */
    public ItemCarrinhoResponse toResponseDTO(ItemCarrinho itemCarrinho) {

        // CÁLCULO MANUAL DO SUBTOTAL (equivalente à 'expression' do MapStruct)
        BigDecimal quantidade = new BigDecimal(itemCarrinho.getQuantidade());
        BigDecimal preco = itemCarrinho.getPrecoUnitarioNaCompra();
        BigDecimal subtotal = preco.multiply(quantidade);

        return ItemCarrinhoResponse.builder()
                .id(itemCarrinho.getId())                                   // source = "id"
                .produtoId(itemCarrinho.getProduto().getId())               // source = "produto.id"
                .nomeProduto(itemCarrinho.getProduto().getNome())           // source = "produto.nome"
                .quantidade(itemCarrinho.getQuantidade())
                .precoUnitarioNaCompra(itemCarrinho.getPrecoUnitarioNaCompra())
                // O MapStruct usava 'expression', fazemos o cálculo manual aqui:
                .subtotal(subtotal.doubleValue()) // Assumindo que o subtotal no Response é Double
                .build();
    }
}
