package com.pontodosjogos.pontodosjogos.mapper;

import com.pontodosjogos.pontodosjogos.controller.request.UsuarioRequest;
import com.pontodosjogos.pontodosjogos.controller.response.UsuarioResponse;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface UsuarioMapper {


    @Mapping(target = "senha", ignore = true)
    @Mapping(target = "endereco", source = "enderecos")
    UsuarioResponse toResponse(Usuario usuario);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "carrinhos", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    Usuario toEntity(UsuarioRequest request);


}
