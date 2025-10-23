package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.ItemCarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ItemCarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.entity.ItemCarrinho;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemCarrinhoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "carrinho", source = "carrinho") //
    @Mapping(target = "produto", source = "produto")   //
    @Mapping(target = "precoUnitarioNaCompra", source = "produto.preco")
    @Mapping(target = "quantidade", source = "request.quantidade")

    ItemCarrinho toEntity(ItemCarrinhoRequest request, Carrinho carrinho, Produto produto);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    @Mapping(target = "precoUnitario", source = "precoUnitarioNaCompra")
    @Mapping(target = "subtotal", expression = "java(itemCarrinho.getPrecoUnitarioNaCompra().multiply(new BigDecimal(itemCarrinho.getQuantidade())))")
    ItemCarrinhoResponse toResponseDTO(ItemCarrinho itemCarrinho);
}
