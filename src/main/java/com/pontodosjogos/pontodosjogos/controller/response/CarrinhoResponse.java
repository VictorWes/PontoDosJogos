package com.pontodosjogos.pontodosjogos.controller.response;

import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import lombok.Builder;

@Builder
public record CarrinhoResponse(
        StatusCarrinho statusInicial
) {
}
