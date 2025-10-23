package com.pontodosjogos.pontodosjogos.controller.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UsuarioResponse(
        String nome,
        String email,
        List<EnderecoResponse> endereco
) {
}
