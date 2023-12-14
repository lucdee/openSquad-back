package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.Squad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SquadMapper {

    SquadDTO map(Squad squad);

    Squad map(SquadDTO squad);

    List<Squad> mapToEntity(List<SquadDTO> squadDTOS);

    List<SquadDTO> mapToDTO(List<Squad> squads);
}
