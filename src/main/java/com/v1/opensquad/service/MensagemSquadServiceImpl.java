package com.v1.opensquad.service;

import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.MensagemSquad;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import com.v1.opensquad.mapper.MensagemSquadMapper;
import com.v1.opensquad.mapper.SquadMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.MensagemSquadRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.repository.SquadRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MensagemSquadServiceImpl implements MensagemSquadService{

    private final MensagemSquadMapper mensagemSquadMapper;

    private final MensagemSquadRepository mensagemSquadRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final SquadRepository squadRepository;

    private final ParticipanteRepository participanteRepository;

    private final SquadMapper squadMapper;


    @Override
    public MensagemSquadDTO save(String token, Long idsquad,MensagemSquadDTO mensagemSquadDTO) {
      Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
      if(autenticacao == null){
          throw new AuthDataException("Token Inválido!");
      }
      Optional<Squad> squad =  squadRepository.findById(idsquad);

      Participante participante= participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idsquad);
      if(participante == null){
          throw new AuthDataException("Somente participantes do squad podem mandar mensagens");
      }
      if(squad.isPresent()){
          mensagemSquadDTO.setDataEnvio(LocalDateTime.now());
          mensagemSquadDTO.setSquad(squad.get());
          mensagemSquadDTO.setParticipante(participante);
          MensagemSquad mensagemSquad =  mensagemSquadRepository.save(mensagemSquadMapper.map(mensagemSquadDTO));
          return mensagemSquadMapper.map(mensagemSquad);
      }else {
          throw new AuthDataException("Squad Inexistente");
      }
    }

    @Override
    public List<MensagemSquadDTO> findBySquad(String token, Long idsquad) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idsquad);
        if(participante == null){
            throw new AuthDataException("É preciso participar do squad para enviar mensagens!");
        }
        List<MensagemSquad> mensagemSquads =  mensagemSquadRepository.findBySquadId(idsquad);
       List<MensagemSquadDTO> mensagemSquadList =   mensagemSquadMapper.map(mensagemSquads);
        return mensagemSquadList;
    }

    @Override
    public String deleteById(String token, Long idMensagem) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
      Optional<MensagemSquad> mensagemSquad =  mensagemSquadRepository.findById(idMensagem);
        if(mensagemSquad.isPresent()){
            if(mensagemSquad.get().getParticipante().getIdPerfil().getId().equals(autenticacao.getIdPerfil().getId())){
                mensagemSquadRepository.deleteById(idMensagem);
                return "deletada";
            }
        }
      return "Erro ao deletar";

    }
}
