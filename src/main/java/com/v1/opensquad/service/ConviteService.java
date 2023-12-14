package com.v1.opensquad.service;

import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.ParticipanteDTO;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ConviteService {

    ConviteDTO save(String token, ConviteDTO conviteDTO, String usuario, Long idsquad);

    List<ConviteDTO> convitesPorPerfil(String token);


    ParticipanteDTO alterarStatus(String token, Integer idConvite, String status);
}
