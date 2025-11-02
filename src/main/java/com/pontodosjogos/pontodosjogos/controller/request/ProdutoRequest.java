package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProdutoRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidadeEmEstoque) {
}
