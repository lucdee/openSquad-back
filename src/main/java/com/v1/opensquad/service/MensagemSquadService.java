package com.v1.opensquad.service;

import com.v1.opensquad.dto.MensagemSquadDTO;

import java.util.List;

public interface MensagemSquadService {

    MensagemSquadDTO save(String token, Long idsquad,MensagemSquadDTO mensagemSquadDTO);

    List<MensagemSquadDTO> findBySquad(String token, Long idsquad);
}
