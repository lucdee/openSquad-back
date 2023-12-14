package com.v1.opensquad.service;

import com.v1.opensquad.dto.VagaDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import com.v1.opensquad.entity.Vaga;
import com.v1.opensquad.mapper.VagaMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.ParticipanteRepository;
import com.v1.opensquad.repository.SquadRepository;
import com.v1.opensquad.repository.VagaRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class VagaServiceImpl implements VagaService{

    private final VagaRepository vagaRepository;

    private final VagaMapper vagaMapper;

    private final ParticipanteRepository participanteRepository;

    private final AutenticacaoRepository autenticacaoRepository;

    private final SquadRepository squadRepository;

    @Override
    public VagaDTO save(String token,VagaDTO vagaDTO, Long idsquad) {


        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token não existente");
        }
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(autenticacao.getIdPerfil().getId(), idsquad);
        Optional<Squad> squad = squadRepository.findById(idsquad);
        if(!squad.isPresent()){
            throw new AuthDataException("Squad não existe");
        }
        vagaDTO.setIdpublicador(participante);
        vagaDTO.setIdsquad(squad.get());
        Vaga vaga = vagaRepository.save(vagaMapper.map(vagaDTO));
        return vagaMapper.map(vaga);
    }

    @Override
    public List<VagaDTO> findBySquad(Long idsquad) {
        List<Vaga> vagas = vagaRepository.findByIdsquadId(idsquad);
        return vagaMapper.map2(vagas);
    }

    @Override
    public VagaDTO findById(Long idVaga) {
        Optional<Vaga> vagas = vagaRepository.findById(idVaga);
        return vagaMapper.map(vagas.get());
    }
}
