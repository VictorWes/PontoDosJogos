package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

@Builder
public record ProdutoRequest(
        String nome,
        String descricao,
        Double preco,
        Integer quantidadeEmEstoque) {
}
