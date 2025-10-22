package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProdutoMapper {

    public static Produto toProduto(ProdutoRequest produtoRequest){
        return Produto.builder()
                .nome(produtoRequest.nome())
                .descricao(produtoRequest.descricao())
                .preco(produtoRequest.preco())
                .quantidadeEmEstoque(produtoRequest.quantidadeEmEstoque())
                .build();
    }

    public static ProdutoResponse toReponse(Produto produto){
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidadeEmEstoque(produto.getQuantidadeEmEstoque())
                .build();
    }

}
