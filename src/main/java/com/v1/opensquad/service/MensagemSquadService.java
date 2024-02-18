package com.v1.opensquad.service;

import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.entity.MensagemSquad;

import java.util.List;

public interface MensagemSquadService {

    MensagemSquadDTO save(String token, Long idsquad,MensagemSquadDTO mensagemSquadDTO);

    List<MensagemSquadDTO> findBySquad(String token, Long idsquad);

    String deleteById(String token, Long idMensagem);
}
