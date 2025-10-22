package com.pontodosjogos.pontodosjogos.controller.request;

import lombok.Builder;

@Builder
public record EnderecoRequest(

         String rua,

         String numero,

         String complemento,

         String bairro,

         String cidade,

         String estado,

         String cep

) {

}
