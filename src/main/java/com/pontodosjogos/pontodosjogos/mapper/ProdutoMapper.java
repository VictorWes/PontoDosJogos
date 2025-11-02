package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


public class ProdutoMapper {

    public Produto toEntity(ProdutoRequest requestDTO) {

        return Produto.builder()
                .nome(requestDTO.nome())
                .descricao(requestDTO.descricao())
                .preco(requestDTO.preco())
                .quantidadeEmEstoque(requestDTO.quantidadeEmEstoque())
                .build();
    }


    public void updateEntityFromDto(ProdutoRequest requestDTO, Produto produto) {
        produto.setNome(requestDTO.nome());
        produto.setDescricao(requestDTO.descricao());
        produto.setPreco(requestDTO.preco());
        produto.setQuantidadeEmEstoque(requestDTO.quantidadeEmEstoque());

    }

    public ProdutoResponse toResponseDTO(Produto produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .preco(produto.getPreco())
                .quantidadeEmEstoque(produto.getQuantidadeEmEstoque())
                .build();
    }


    public List<ProdutoResponse> toResponseDTOList(List<Produto> produtos) {
        return produtos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


}
