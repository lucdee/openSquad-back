package com.v1.opensquad.service;

import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;

import java.util.List;

public interface TarefaService {

 TarefaDTO save(String token, Long idHistoria, TarefaDTO tarefaDTO);

 List<TarefaDTO> findByHistoria(Long idHistoria);
}
