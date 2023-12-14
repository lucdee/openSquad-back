package com.v1.opensquad.service;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.entity.Squad;
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
        perfil.ifPresent(participante::setIdPerfil);

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
