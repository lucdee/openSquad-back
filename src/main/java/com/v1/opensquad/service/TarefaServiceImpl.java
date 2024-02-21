package com.v1.opensquad.service;


import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;
import com.v1.opensquad.dto.TarefasListStatusDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Tarefa;
import com.v1.opensquad.mapper.HistoriaMapper;
import com.v1.opensquad.mapper.TarefaMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.HistoriaRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.repository.TarefaRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final ParticipanteRepository participanteRepository;


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

        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), tarefaDTO.getIdHistoria().getIdParticipante().getIdSquad().getId());
        if(participante == null){
            throw new AuthDataException("Somentes participantes do squad podem criar tarefas.");
        }
        tarefaDTO.setInicioData(String.valueOf(LocalDateTime.now()));
        tarefaDTO.setStatus(1);
        Tarefa tarefa = tarefaMapper.map(tarefaDTO);
        Tarefa tarefa1 = tarefaRepository.save(tarefa);
        return tarefaMapper.map(tarefa1);
    }

    @Override
    public List<TarefaDTO> findByHistoria(Long idHistoria) {
        List<Tarefa> tarefas = tarefaRepository.findByIdHistoriaId(idHistoria);
        return tarefaMapper.map3(tarefas);
    }

    @Override
    public TarefasListStatusDTO findAll(Long idSquad) {
        List<Tarefa> tarefas = new ArrayList<>();
        List<Participante> participantes = participanteRepository.findByIdSquadId(idSquad);

        for (Participante participante : participantes) {
            List<Historia> historialist = historiaRepository.findByIdParticipanteId(participante.getId());

            for(Historia historia : historialist){
                tarefas.addAll(tarefaRepository.findByIdHistoriaId(historia.getId()));
            }
        }

        List<TarefaDTO> tarefaDTOS = tarefaMapper.map3(tarefas);
        TarefasListStatusDTO tarefasListStatusDTO = new TarefasListStatusDTO();

        for (TarefaDTO tarefaDto : tarefaDTOS) {
            if (tarefaDto.getStatus().equals(1)) {
                tarefasListStatusDTO.getListaBacklog().add(tarefaDto);
            } else if (tarefaDto.getStatus().equals(2)) {
                tarefasListStatusDTO.getListaEmDesenvolvimento().add(tarefaDto);
            } else if (tarefaDto.getStatus().equals(3)) {
                tarefasListStatusDTO.getListaEmTeste().add(tarefaDto);
            } else if (tarefaDto.getStatus().equals(4)) {
                tarefasListStatusDTO.getListaPronto().add(tarefaDto);
            }
        }

        return tarefasListStatusDTO;
    }


    @Override
    public TarefaDTO avancarStatus(String token, Long idtarefa) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idtarefa);

        if(tarefa.get().getStatus() > 4){
            throw new AuthDataException("O item já está arquivado");
        }
        tarefa.ifPresent(value -> value.setStatus(value.getStatus() + 1));
        Tarefa tarefa1 =   tarefaRepository.save(tarefa.get());
        TarefaDTO tarefaDTO = tarefaMapper.map(tarefa1);
        return tarefaDTO;
    }
}
