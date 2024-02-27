package com.v1.opensquad.service;

import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.*;
import com.v1.opensquad.mapper.CategoriaSquadMapper;
import com.v1.opensquad.mapper.SquadMapper;
import com.v1.opensquad.repository.*;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class SquadServiceImpl implements SquadService{

    public final SquadRepository squadRepository;

    public final AutenticacaoRepository autenticacaoRepository;

    public final PerfilRepository perfilRepository;

    public final ParticipanteService participanteService;


    public final VagaRepository vagaRepository;

    public final CategoriaSquadRepository categoriaSquadRepository;

    public final CategoriaSquadMapper categoriaSquadMapper;

    public final ParticipanteRepository participanteRepository;

    public final AutenticacaoService autenticacaoService;

    public final HistoriaRepository historiaRepository;

    public final TarefaRepository tarefaRepository;

    public final SquadMapper squadMapper;

    private static String caminhoImagens = "C://Users/bocsg/OneDrive/Documentos/opensquad/";

    public final MensagemSquadService mensagemSquadService;

    public final CurtidaSquadRepository curtidaSquadRepository;


    @Override
    public SquadDTO save(String token, SquadDTO squadDTO, String funcaoCriador, Integer cargaHoraria, Integer categoriaId) {
       Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
       if(autenticacao == null){
           throw new AuthDataException("Token não existente");
       }

        Optional<CategoriaSquad> categoriaSquad = categoriaSquadRepository.findById(Long.valueOf(categoriaId));
        categoriaSquad.ifPresent(squad -> squadDTO.setArea(categoriaSquadMapper.map(squad)));

        squadDTO.setStatus("A");
        squadDTO.setCurtidas(0);
        squadDTO.setMembros(1);

        squadDTO.setDataCriacao(String.valueOf(LocalDateTime.now()));
        Squad squad = squadRepository.save(squadMapper.map(squadDTO));

        ParticipanteDTO participanteDTO = new ParticipanteDTO();
       participanteDTO.setIdSquad(squadMapper.map(squad));
       participanteDTO.setDataEntrada(String.valueOf(LocalDateTime.now()));
       participanteDTO.setFuncao(funcaoCriador);
       participanteDTO.setStatus("D");
       participanteDTO.setCargaHoraria(cargaHoraria);
       participanteDTO.setParticipacao(100);
       try {
           participanteService.save(token, autenticacao.getIdPerfil().getId(), participanteDTO);
       }catch (Exception e){
           squadRepository.deleteById(squad.getId());
       }
     return squadMapper.map(squad);
    }

    @Override
    public List<SquadDTO> findByToken(String token) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);

        autenticacao.getIdPerfil();

        List<Participante> participanteList = participanteRepository.findByIdPerfilId(autenticacao.getIdPerfil().getId());

        List<Squad> squads = new ArrayList<>();
        for (Participante participante : participanteList) {
            Optional<Squad> squad = squadRepository.findById(participante.getIdSquad().getId());
            squad.ifPresent(squads::add);
        }

        return squadMapper.mapToDTO(squads);
    }


    @Override
    public List<SquadDTO> findAll() throws IOException {
            List<Squad> squad = squadRepository.findAll();
            for (Squad squad2 : squad) {
            if (squad2.getCurtidas() == null) {
                squad2.setCurtidas(0);
                squadRepository.save(squad2);
            }
        }
           Collections.sort(squad, Comparator.comparingInt(Squad::getCurtidas).reversed());
            return  squadMapper.mapToDTO(squad);
    }

    @Override
    public List<SquadDTO> findByCategoria(Integer idcategoria, String nomesquad) throws IOException {

        if(idcategoria == null && (nomesquad == null || nomesquad.isEmpty())){
            List<Squad> squads = squadRepository.findAll();
            return squadMapper.mapToDTO(squads);
        }

        if(idcategoria == null){
            List<Squad> squads = squadRepository.findByNome(nomesquad);
            return squadMapper.mapToDTO(squads);
        }

        List<Squad> squads = squadRepository.findByAreaIdAndNomeContainingIgnoreCase(Long.valueOf(idcategoria), (nomesquad!=null ? nomesquad : ""));
        return  squadMapper.mapToDTO(squads);
    }

    @Override
    public String salvarFotoSquad(String token, Long idsquad, String urlFoto) {
        //salvando imagem
        Optional<Squad> squad = squadRepository.findById(idsquad);
        if(squad.isPresent()) {
            squad.get().setImgSquad(urlFoto);
            squadRepository.save(squad.get());
        }
        return "imagem salva";
    }

    @Override
    public SquadDTO findById(Long id) {
      Optional<Squad> squad = squadRepository.findById(id);
    if(squad!= null){
        return squadMapper.map(squad.get());
    }
    return null;
    }

    @Override
    public SquadDTO deleteById(Long id, String token) {
        RetornoPerfilDTO retornoPerfilDTO = autenticacaoService.verificarPerfil(token);
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(retornoPerfilDTO.getId(), id);

         List<Historia> historias = historiaRepository.findByIdParticipanteReporterId(participante.getId());
         for(Historia historia: historias) {
             List<Tarefa> tarefas = tarefaRepository.findByIdHistoriaId(historia.getId());
             for (Tarefa tarefa : tarefas) {
                 tarefaRepository.deleteById(tarefa.getId());
             }
             historiaRepository.deleteById(historia.getId());
         }
          List<MensagemSquadDTO> mensagemSquads =  mensagemSquadService.findBySquad(token, id);
         for(MensagemSquadDTO mensagemSquadDTO : mensagemSquads){
             mensagemSquadService.deleteById(token, mensagemSquadDTO.getId());
         }

         List<Vaga> vagas = vagaRepository.findByIdsquadId(id);
         for(Vaga vaga : vagas){
             vagaRepository.deleteById(vaga.getId());
         }
           List<Participante> participantes = participanteRepository.findByIdSquadId(id);
           for(Participante participante1: participantes){

               participanteRepository.deleteById(participante1.getId());

           }
            Optional<Squad> squad = squadRepository.findById(id);
            if(squad.isPresent()){
                squadRepository.deleteById(id);
                return squadMapper.map(squad.get());
            }

        return null;
    }


    @Override
    public String curtirSquad(Long idSquad, String token) {
        RetornoPerfilDTO retornoPerfilDTO = autenticacaoService.verificarPerfil(token);
        if(retornoPerfilDTO == null){
            throw new AuthDataException("Token invalido");
        }


        CurtidaSquad curtidaSquad = new CurtidaSquad();
        curtidaSquad.setIdSquad(idSquad);
        curtidaSquad.setIdPerfil(retornoPerfilDTO.getId());
        if(curtidaSquadRepository.findByIdSquadAndAndIdPerfil(idSquad, retornoPerfilDTO.getId()) != null){
            throw new AuthDataException("Esse perfil ja curtiu o squad");
        }
        curtidaSquadRepository.save(curtidaSquad);
        Optional<Squad> squad =  squadRepository.findById(idSquad);
        if(squad.isPresent()){
            List<CurtidaSquad> curtidaSquads = curtidaSquadRepository.findByIdSquad(idSquad);
            squad.get().setCurtidas(curtidaSquads.size());
            squadRepository.save(squad.get());
        }
        return "Squad Curtido!";
    }

    @Override
    public SquadDTO edit(String token, Long idSquad, SquadDTO squadDTO) {
        RetornoPerfilDTO retornoPerfilDTO = autenticacaoService.verificarPerfil(token);
        if(retornoPerfilDTO == null){
            throw new AuthDataException("Token invalido");
        }
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(retornoPerfilDTO.getId(), idSquad);
       if(participante == null){
           throw new AuthDataException("Você não participa do squad");
       }
        Optional<Squad> squad = squadRepository.findById(idSquad);
       if(squad.isPresent()){
           squad.get().setNome(squadDTO.getNome());
           squad.get().setDescricao(squadDTO.getDescricao());

           Optional<CategoriaSquad> categoriaSquad = categoriaSquadRepository.findById(squadDTO.getArea().getId());
           if(categoriaSquad.isPresent()){
               squad.get().setArea(categoriaSquad.get());
                Squad squad1 =  squadRepository.save(squad.get());
                return squadMapper.map(squad1);
           }

       }
        throw new AuthDataException("Squad não existe");

    }


}
