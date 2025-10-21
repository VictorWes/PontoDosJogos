package com.pontodosjogos.pontodosjogos.controller.response;

import lombok.Builder;

@Builder
public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer quantidadeEmEstoque) {
}
