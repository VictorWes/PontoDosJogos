package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.EnderecoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.EnderecoResponse;
import com.pontodosjogos.pontodosjogos.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    // Instância estática para uso fora do Spring Context (útil em testes ou métodos default de outros mappers)

    // =================================================================
    // 1. Mapeamento de Requisição (Request) para Entidade (JPA) - CRIAÇÃO
    // =================================================================

    // Usado ao adicionar um novo endereço. O campo 'usuario' (FK) será setado no Service.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true) // Ignora a Entidade Usuario (o Service fará a vinculação)
    Endereco toEntity(EnderecoRequest request);

    // =================================================================
    // 2. Mapeamento de Entidade (JPA) para Resposta (Response) - BUSCA
    // =================================================================

    // Usado ao retornar o endereço para o frontend.
    EnderecoResponse toResponse(Endereco endereco);

    // =================================================================
    // 3. Mapeamento de Fusão (Request para Entidade existente) - ATUALIZAÇÃO
    // =================================================================

    // Usado no EnderecoService para o método 'atualizarEndereco'.
    // Copia os dados do DTO para a Entidade já buscada.
    @Mapping(target = "id", ignore = true) // Protege o ID de ser sobrescrito
    @Mapping(target = "usuario", ignore = true) // Protege a FK de ser sobrescrita/desvinculada
    void updateEntityFromDto(EnderecoRequest request, @MappingTarget Endereco endereco);
}
