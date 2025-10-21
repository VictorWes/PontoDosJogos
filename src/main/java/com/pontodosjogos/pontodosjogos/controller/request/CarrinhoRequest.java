package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

@Builder
public record CarrinhoRequest(
        Long usuarioId,
        Long jogoId) {
}
