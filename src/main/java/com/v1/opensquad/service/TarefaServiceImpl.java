package com.v1.opensquad.service;


import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Tarefa;
import com.v1.opensquad.mapper.HistoriaMapper;
import com.v1.opensquad.mapper.TarefaMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.HistoriaRepository;
import com.v1.opensquad.repository.TarefaRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository tarefaRepository;


    private final TarefaMapper tarefaMapper;

    private final HistoriaRepository historiaRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final HistoriaMapper historiaMapper;


    @Override
    public TarefaDTO save(String token, Long idHistoria, TarefaDTO tarefaDTO) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        Optional<Historia> historia = historiaRepository.findById(idHistoria);
        if(historia.isPresent()){
            tarefaDTO.setIdHistoria(historia.get());
        }else {
            throw new AuthDataException("historia inexistente!");
        }
        tarefaDTO.setInicioData(String.valueOf(LocalDateTime.now()));
        Tarefa tarefa = tarefaMapper.map(tarefaDTO);
        Tarefa tarefa1 = tarefaRepository.save(tarefa);
        return tarefaMapper.map(tarefa1);
    }

    @Override
    public List<TarefaDTO> findByHistoria(Long idHistoria) {
        List<Tarefa> tarefas = tarefaRepository.findByIdHistoriaId(idHistoria);
        return tarefaMapper.map3(tarefas);
    }
}
