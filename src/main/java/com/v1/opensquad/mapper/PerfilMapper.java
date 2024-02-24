package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.entity.RetornoPerfil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    Perfil map(PerfilDTO perfilDto);

    PerfilDTO map(Perfil perfil);

    RetornoPerfilDTO mapRetorno(Perfil perfil);

    RetornoPerfil mapRetorno(RetornoPerfilDTO retornoPerfilDTO);
}
