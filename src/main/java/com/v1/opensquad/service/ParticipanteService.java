package com.v1.opensquad.service;

import com.v1.opensquad.dto.ParticipanteDTO;

import java.util.List;

public interface ParticipanteService {

   ParticipanteDTO save(String token, Long idPerfil, ParticipanteDTO participanteDTO);

   List<ParticipanteDTO> findBySquad(Long idsquad);

   String verificarStatusParticipantePorSquad(String token, Long idsquad);
}
