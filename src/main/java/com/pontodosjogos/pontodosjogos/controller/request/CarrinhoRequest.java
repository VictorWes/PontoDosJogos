package com.pontodosjogos.pontodosjogos.controller.request;

import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import lombok.Builder;

@Builder
public record CarrinhoRequest(
        Long usuarioId,

        StatusCarrinho statusInicial) {
}
