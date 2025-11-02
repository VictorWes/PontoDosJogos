package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.UsuarioRequest;
import com.pontodosjogos.pontodosjogos.controller.response.EnderecoResponse;
import com.pontodosjogos.pontodosjogos.controller.response.UsuarioResponse;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component // Marca a classe como um Bean injetável pelo Spring
@RequiredArgsConstructor
public class UsuarioMapper {


    // Injeta o EnderecoMapper, que também deve ser um @Component manual
    private final EnderecoMapper enderecoMapper;

    // =================================================================
    // 1. DTO de Requisição (Request) para Entidade (JPA) - CRIAÇÃO
    // (UsuarioRequest -> Usuario)
    // =================================================================

    /**
     * Mapeia o DTO de Requisição para a Entidade Usuario.
     * Campos de relacionamento (carrinhos, enderecos) são ignorados pois são setados no Service.
     */
    public Usuario toEntity(UsuarioRequest request) {
        // O Lombok @Builder é útil aqui
        return Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(request.senha()) // A senha será hashada no Service
                // Os campos 'id', 'carrinhos' e 'enderecos' são ignorados/inicializados
                .carrinhos(Collections.emptyList())
                .enderecos(Collections.emptyList())
                .build();
    }


    // =================================================================
    // 2. Entidade (JPA) para DTO de Resposta (Response)
    // (Usuario -> UsuarioResponse)
    // =================================================================

    /**
     * Mapeia a Entidade Usuario para o DTO de Resposta, expondo apenas dados seguros.
     */
    public UsuarioResponse toResponse(Usuario usuario) {
        // Mapeia a lista de endereços para o DTO de Resposta
        List<EnderecoResponse> enderecosResponse = usuario.getEnderecos().stream()
                .map(enderecoMapper::toResponse)
                .collect(Collectors.toList());

        return UsuarioResponse.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                // A senha é omitida
                .endereco(enderecosResponse) // A lista de EnderecoResponse
                .build();
    }


}
