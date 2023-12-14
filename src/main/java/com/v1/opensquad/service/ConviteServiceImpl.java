package com.v1.opensquad.service;


import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.*;
import com.v1.opensquad.mapper.ConviteMapper;
import com.v1.opensquad.mapper.PerfilMapper;
import com.v1.opensquad.mapper.SquadMapper;
import com.v1.opensquad.repository.*;
import com.v1.opensquad.service.exception.AuthDataException;
import com.v1.opensquad.service.exception.ProfileDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConviteServiceImpl implements ConviteService{

    private final ConviteRepository conviteRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final ParticipanteService participanteService;

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    private final SquadRepository squadRepository;

    private final SquadMapper squadMapper;


    private final ConviteMapper conviteMapper;

    private final SquadServiceImpl squadService;


    @Override
    public ConviteDTO save(String token, ConviteDTO conviteDTO,  String usuario, Long idsquad) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
       List<Perfil> perfil =  perfilRepository.findByUsuario(usuario);

        if(perfil.size() == 0){
            throw new ProfileDataException("Usuário não encontrado");
        }

       Optional<Squad> squad = squadRepository.findById(idsquad);

       conviteDTO.setDataConvite(String.valueOf(LocalDateTime.now()));
       if(squad.isPresent()){
           conviteDTO.setIdSquad(squadMapper.map(squad.get()));
       }
       if(perfil.size() != 0){
           conviteDTO.setIdPerfilEnvio(perfilMapper.map(perfil.get(0)));
       }
       conviteDTO.setStatus("E");
       Convite convite = conviteMapper.map(conviteDTO);
       Convite convite1 = conviteRepository.save(convite);
       return conviteMapper.map(convite1);
    }

    @Override
    public List<ConviteDTO> convitesPorPerfil(String token) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
       List<Convite> convites = conviteRepository.findByIdPerfilEnvioId(autenticacao.getIdPerfil().getId());
        List<ConviteDTO> conviteDTOS = conviteMapper.map2(convites);

        List<SquadDTO> squadDTOS = new ArrayList<>();
        for(ConviteDTO conviteDTO : conviteDTOS){
           squadDTOS.add(conviteDTO.getIdSquad());
        }
        List<SquadDTO> squadDTOS1 = squadService.getSquadDTOSImages(squadMapper.mapToEntity(squadDTOS));

        for(ConviteDTO conviteDTO : conviteDTOS){
            for(SquadDTO squadDTO : squadDTOS1){
                conviteDTO.setIdSquad(squadDTO);
            }
        }

        return conviteDTOS;
    }

    @Override
    public ParticipanteDTO alterarStatus(String token, Integer idConvite, String status) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }

        Optional<Convite> convite = conviteRepository.findById(idConvite);
        if(convite.isPresent()){
         convite.get().setStatus(status);
         conviteRepository.save(convite.get());
        }
        //Aceito
        if(status.equals("A")){
            ParticipanteDTO participanteDTO = new ParticipanteDTO();
            participanteDTO.setStatus("A");
            participanteDTO.setIdSquad(squadMapper.map(convite.get().getIdSquad()));
            participanteDTO.setFuncao(convite.get().getFuncao());
            participanteDTO.setParticipacao(Integer.valueOf(convite.get().getParticipacao()));
            participanteDTO.setCargaHoraria(Math.toIntExact(convite.get().getCargahoraria()));
            participanteDTO.setDataEntrada(String.valueOf(LocalDateTime.now()));
            participanteDTO.setIdPerfil(perfilMapper.map(convite.get().getIdPerfilEnvio()));

            try {
                ParticipanteDTO participante = participanteService.save(token, convite.get().getIdPerfilEnvio().getId(), participanteDTO);
                conviteRepository.deleteById(idConvite);
                return participante;
            }catch (Exception e){
                throw new AuthDataException("Erro ao salvar participante");
            }
        }

        //Recusado
        if(status.equals("R")){
         conviteRepository.deleteById(idConvite);
        }
        throw new AuthDataException("Convite não encontrado!");

    }
}
