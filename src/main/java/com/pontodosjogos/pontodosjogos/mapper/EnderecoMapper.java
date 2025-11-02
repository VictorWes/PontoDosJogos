package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.EnderecoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.EnderecoResponse;
import com.pontodosjogos.pontodosjogos.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


public class EnderecoMapper {

    // Assumimos que a Entidade Endereco tem todos os getters e setters

    // =================================================================
    // 1. Mapeamento de Requisição (Request) para Entidade (JPA) - CRIAÇÃO
    // Equivalente a: Endereco toEntity(EnderecoRequest request)
    // =================================================================
    public Endereco toEntity(EnderecoRequest request) {
        return Endereco.builder()
                .rua(request.rua())
                .numero(request.numero())
                .complemento(request.complemento())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .estado(request.estado())
                .cep(request.cep())
                // 'id' e 'usuario' são ignorados/setados no Service
                .build();
    }

    // =================================================================
    // 2. Mapeamento de Entidade (JPA) para Resposta (Response) - BUSCA
    // Equivalente a: EnderecoResponse toResponse(Endereco endereco)
    // =================================================================
    public EnderecoResponse toResponse(Endereco endereco) {
        return EnderecoResponse.builder()
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .build();
    }

    // =================================================================
    // 3. Mapeamento de Fusão (Request para Entidade existente) - ATUALIZAÇÃO
    // Equivalente a: void updateEntityFromDto(EnderecoRequest request, @MappingTarget Endereco endereco)
    // =================================================================
    public void updateEntityFromDto(EnderecoRequest request, Endereco endereco) {
        // Copia os dados do DTO para a Entidade existente.
        // O ID e o USUARIO (FK) são preservados.
        endereco.setRua(request.rua());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        endereco.setCep(request.cep());
    }
}
