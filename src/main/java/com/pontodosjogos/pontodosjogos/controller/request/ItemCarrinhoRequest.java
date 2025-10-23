package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

@Builder
public record ItemCarrinhoRequest(
        Long produtoId,
        Integer quantidade,
        Double precoUnitarioNaCompra
) {

}
