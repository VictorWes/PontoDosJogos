package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemCarrinhoRequest(
        Long produtoId,
        Integer quantidade
) {

}
