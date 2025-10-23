package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.CarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.CarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import lombok.experimental.UtilityClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ItemCarrinhoMapper.class, UsuarioMapper.class})
public interface CarrinhoMapper {



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "itens", ignore = true) // A lista de itens deve ser gerenciada à parte
    @Mapping(target = "dataCriacao", expression = "java(java.time.LocalDate.now())") // Define a data
    @Mapping(target = "statusCarrinho", source = "statusInicial")
    @Mapping(target = "usuario", source = "usuarioId")
    Carrinho toEntity(CarrinhoRequest request, Usuario usuario);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "statusCarrinho")
    @Mapping(target = "dataCriacao", source = "dataCriacao")
    @Mapping(target = "itens", source = "itens") // MapStruct usa o ItemCarrinhoMapper
    @Mapping(target = "totalCarrinho", ignore = true) // O Service deve calcular este campo
    CarrinhoResponse toFullResponseDTO(Carrinho carrinho);


}
