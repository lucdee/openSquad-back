package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.entity.CategoriaSquad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaSquadMapper {

    CategoriaSquadDTO map(CategoriaSquad categoriaSquad);

    CategoriaSquad map(CategoriaSquadDTO categoriaSquadDTO);

    List<CategoriaSquadDTO> map2(List<CategoriaSquad> categoriaSquad);
}
