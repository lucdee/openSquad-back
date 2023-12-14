package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.entity.Participante;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipanteMapper {

    ParticipanteDTO map(Participante participante);

    Participante map(ParticipanteDTO participante);

    List<ParticipanteDTO> map2(List<Participante> participanteList);
}
