package com.v1.opensquad.service;

import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.HistoriasListStatusDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Tarefa;
import com.v1.opensquad.mapper.HistoriaMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.HistoriaRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.repository.TarefaRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoriaServiceImpl implements HistoriaService{

    private final ParticipanteRepository participanteRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final HistoriaRepository historiaRepository;

    private final HistoriaMapper historiaMapper;

    private final TarefaRepository tarefaRepository;

    @Override
    public HistoriaDTO save(Long idSquad, String token, HistoriaDTO historiaDTO) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
      Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idSquad);

        if(participante == null){
            throw new AuthDataException("Somente participantes do squad podem criar histórias.");
        }
      historiaDTO.setIdParticipanteReporter(participante);
      historiaDTO.setStatus(1);
      historiaDTO.setInicialData(String.valueOf(LocalDateTime.now()));
      Historia historia = historiaRepository.save(historiaMapper.map(historiaDTO));
      return historiaMapper.map(historia);
    }

    @Override
    public HistoriaDTO avancarStatus(String token, Long idHistoria) {
        Optional<Historia> historia = historiaRepository.findById(idHistoria);

        if(historia.get().getStatus() > 4){
            throw new AuthDataException("O item já está arquivado");
        }
        historia.ifPresent(value -> value.setStatus(value.getStatus() + 1));
        Historia historia1 =   historiaRepository.save(historia.get());
        HistoriaDTO historiaDTO = historiaMapper.map(historia1);
        return historiaDTO;
    }

    @Override
    public HistoriasListStatusDTO findHistoriasBySquad(Long idSquad, Long idparticipante) {
        List<Historia> historialist = new ArrayList<>();

        List<Participante> participantes = participanteRepository.findByIdSquadId(idSquad);
        for (Participante participante : participantes) {
            historialist.addAll(historiaRepository.findByIdParticipanteReporterId(participante.getId()));
        }

        if (idparticipante != null) {
            historialist.removeIf(historia -> !historia.getIdParticipanteReporter().getId().equals(idparticipante));
        }

        List<HistoriaDTO> historiaDTOS = historiaMapper.map3(historialist);
        HistoriasListStatusDTO historiasListStatusDTO = new HistoriasListStatusDTO();

        for (HistoriaDTO historia : historiaDTOS) {
            if (historia.getStatus().equals(1)) {
                historiasListStatusDTO.getListaBacklog().add(historia);
            } else if (historia.getStatus().equals(2)) {
                historiasListStatusDTO.getListaEmDesenvolvimento().add(historia);
            }
                else if (historia.getStatus().equals(3)) {
                    historiasListStatusDTO.getListaEmTeste().add(historia);
            } else if (historia.getStatus().equals(4)) {
                historiasListStatusDTO.getListaPronto().add(historia);
            }
        }

        return historiasListStatusDTO;
    }

    @Override
    public List<HistoriaDTO> findAllAtivas(Long idSquad) {
        List<Historia> historialist = new ArrayList<>();

        List<Participante> participantes = participanteRepository.findByIdSquadId(idSquad);
        for (Participante participante : participantes) {
            historialist.addAll(historiaRepository.findByIdParticipanteReporterId(participante.getId()));
        }
        List<Historia> historialistFiltrada = new ArrayList<>();
        for(Historia historia: historialist){
            if(!historia.getStatus().equals(5)){
                historialistFiltrada.add(historia);
            }
        }

        return historiaMapper.map3(historialistFiltrada);
    }

    @Override
    public HistoriaDTO findById(Long idHistoria) {
        return null;
    }

    @Override
    public HistoriaDTO adicionarAssigneeHistoria(String token, Long idHistoria, Long idParticipante, Long idSquad) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idSquad);

        if(participante == null){
            throw new AuthDataException("Participante nao encontrado!");
        }

       Optional<Participante> participante1 = participanteRepository.findById(idParticipante);
        if(participante1.isPresent()){
            Optional<Historia> historia =  historiaRepository.findById(idHistoria);
            if(historia.isPresent()){
                historia.get().setIdParticipanteAssignee(participante1.get());
                Historia historia1 = historiaRepository.save(historia.get());
                return historiaMapper.map(historia1);
            }
        }else {
            throw new AuthDataException("Participante nao encontrado");
        }

       return  null;

    }

    @Override
    public String deleteById(String token, Long idHistoria, Long idSquad) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idSquad);

        if(participante == null){
            throw new AuthDataException("Participante nao encontrado!");
        }
        Optional<Historia> historia = historiaRepository.findById(idHistoria);
        if(historia.isPresent()){
           List<Tarefa> tarefas = tarefaRepository.findByIdHistoriaId(historia.get().getId());
           for(Tarefa tarefa : tarefas){
               tarefaRepository.deleteById(tarefa.getId());
           }
            historiaRepository.deleteById(historia.get().getId());
            return "História Deletada";
        }else {
            throw new AuthDataException("história não encontrada!");
        }
    }
}
