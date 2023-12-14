package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.entity.Perfil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PerfilMapper {

    Perfil map(PerfilDTO perfilDto);

    PerfilDTO map(Perfil perfil);

    RetornoPerfilDTO mapRetorno(Perfil perfil);
}
