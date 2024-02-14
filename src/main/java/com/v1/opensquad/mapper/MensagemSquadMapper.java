package com.v1.opensquad.mapper;


import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.entity.MensagemSquad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MensagemSquadMapper {

    MensagemSquadDTO map(MensagemSquad mensagemSquad);

    MensagemSquad map(MensagemSquadDTO mensagemSquadDTO);

    List<MensagemSquadDTO> map(List<MensagemSquad> mensagemSquadList);
}
