package com.v1.opensquad.service;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.*;
import com.v1.opensquad.mapper.ParticipanteMapper;
import com.v1.opensquad.mapper.PerfilMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.repository.PerfilRepository;
import com.v1.opensquad.repository.SquadRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ParticipanteServiceImpl implements ParticipanteService{

    private final AutenticacaoRepository autenticacaoRepository;

    private final ParticipanteRepository participanteRepository;

    private final SquadRepository squadRepository;
    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    private final ParticipanteMapper participanteMapper;

    @Override
    public ParticipanteDTO save(String token, Long idPerfil, ParticipanteDTO participanteDTO) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null || !idPerfil.equals(autenticacao.getIdPerfil().getId())){
            throw new AuthDataException("Token Inválido!");
        }
       Participante participante =  participanteMapper.map(participanteDTO);


       Optional<Perfil> perfil = perfilRepository.findById(idPerfil);
        if(perfil != null){
            RetornoPerfilDTO perfilRetorno=  perfilMapper.mapRetorno(perfil.get());
            participante.setIdPerfil(perfilMapper.mapRetorno(perfilRetorno));
        }



        Optional<Squad> squad = squadRepository.findById(participante.getIdSquad().getId());
        if(squad.isPresent()){
            if(squad.get().getMembros() == null){
                squad.get().setMembros(1);
            }
            squad.get().setMembros(squad.get().getMembros() + 1);
            squadRepository.save(squad.get());
        }

        participanteRepository.save(participante);
        return participanteMapper.map(participante);
    }

    @Override
    public List<ParticipanteDTO> findBySquad(Long idsquad) {
       List<Participante> participanteList = participanteRepository.findByIdSquadId(idsquad);
       List<ParticipanteDTO> participanteDTOS = participanteMapper.map2(participanteList);
       return participanteDTOS;
    }

    @Override
    public String verificarStatusParticipantePorSquad(String token, Long idsquad) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        List<Participante> participanteList = participanteRepository.findByIdPerfilId(autenticacao.getIdPerfil().getId());

        for(Participante participante1 : participanteList){
            if(Objects.equals(participante1.getIdSquad().getId(), idsquad)){
               return participante1.getStatus();
            }
        }
       return "Nao participa";
    }
}
