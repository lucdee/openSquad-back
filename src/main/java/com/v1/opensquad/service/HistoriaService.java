package com.v1.opensquad.service;

import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.HistoriasListStatusDTO;

import java.util.List;

public interface HistoriaService {

    HistoriaDTO save(Long idSquad, String token, HistoriaDTO historiaDTO);

    HistoriasListStatusDTO findHistoriasBySquad(Long idSquad, Long idparticipante);

    HistoriaDTO findById(Long idHistoria);

}
