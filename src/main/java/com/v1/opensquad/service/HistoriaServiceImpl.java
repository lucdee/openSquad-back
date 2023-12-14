package com.v1.opensquad.service;

import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.HistoriasListStatusDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.mapper.HistoriaMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.HistoriaRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HistoriaServiceImpl implements HistoriaService{

    private final ParticipanteRepository participanteRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final HistoriaRepository historiaRepository;

    private final HistoriaMapper historiaMapper;

    @Override
    public HistoriaDTO save(Long idSquad, String token, HistoriaDTO historiaDTO) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
      Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idSquad);
      historiaDTO.setIdParticipante(participante);
      historiaDTO.setStatus("B");
      historiaDTO.setInicialData(String.valueOf(LocalDateTime.now()));
      Historia historia = historiaRepository.save(historiaMapper.map(historiaDTO));
      return historiaMapper.map(historia);
    }

    @Override
    public HistoriasListStatusDTO findHistoriasBySquad(Long idSquad, Long idparticipante) {
        List<Historia> historialist = new ArrayList<>();

        List<Participante> participantes = participanteRepository.findByIdSquadId(idSquad);
        for (Participante participante : participantes) {
            historialist.addAll(historiaRepository.findByIdParticipanteId(participante.getId()));
        }

        if (idparticipante != null) {
            historialist.removeIf(historia -> !historia.getIdParticipante().getId().equals(idparticipante));
        }

        List<HistoriaDTO> historiaDTOS = historiaMapper.map3(historialist);
        HistoriasListStatusDTO historiasListStatusDTO = new HistoriasListStatusDTO();

        for (HistoriaDTO historia : historiaDTOS) {
            if ("B".equals(historia.getStatus())) {
                historiasListStatusDTO.getListaBacklog().add(historia);
            } else if ("D".equals(historia.getStatus())) {
                historiasListStatusDTO.getListaEmDesenvolvimento().add(historia);
            } else if ("P".equals(historia.getStatus())) {
                historiasListStatusDTO.getListaPronto().add(historia);
            }
        }

        return historiasListStatusDTO;
    }

    @Override
    public HistoriaDTO findById(Long idHistoria) {
        return null;
    }
}
