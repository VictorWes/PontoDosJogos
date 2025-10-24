package com.pontodosjogos.pontodosjogos.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemCarrinhoResponse(
        Long id, // Para que o frontend possa remover/atualizar
        Long produtoId, // ID do produto
        String nomeProduto, // Nome para exibição
        Integer quantidade,
        BigDecimal precoUnitarioNaCompra,
        Double subtotal
) {

}
