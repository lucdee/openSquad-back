package com.v1.opensquad.service;

import com.v1.opensquad.dto.VagaDTO;

import java.util.List;

public interface VagaService {

    VagaDTO save(String token, VagaDTO vagaDTO, Long idsquad);

    List<VagaDTO> findBySquad(Long idsquad);

    VagaDTO findById(Long idVaga);
}
