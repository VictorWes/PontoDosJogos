package com.pontodosjogos.pontodosjogos.controller.response;

import lombok.Builder;

@Builder
public record EnderecoResponse(
                                String rua,

                               String numero,

                               String complemento,

                               String bairro,

                               String cidade,

                               String estado,

                               String cep) {
}
