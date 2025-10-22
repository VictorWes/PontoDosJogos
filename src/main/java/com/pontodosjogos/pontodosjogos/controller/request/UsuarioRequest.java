package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

@Builder
public record UsuarioRequest(

    String nome,
    String email,
    String senha,
    EnderecoRequest endereco
) {
}
