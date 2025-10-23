package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    Produto toEntity(ProdutoRequest requestDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(ProdutoRequest requestDTO, @MappingTarget Produto produto);

    ProdutoResponse toResponseDTO(Produto produto);

    List<ProdutoResponse> toResponseDTOList(List<Produto> produtos);


}
