package com.pontodosjogos.pontodosjogos.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEmEstoque) {
}
