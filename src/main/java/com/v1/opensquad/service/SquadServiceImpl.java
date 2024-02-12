package com.v1.opensquad.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.*;
import com.v1.opensquad.mapper.CategoriaSquadMapper;
import com.v1.opensquad.mapper.SquadMapper;
import com.v1.opensquad.repository.*;
import com.v1.opensquad.service.exception.AuthDataException;
import com.v1.opensquad.service.exception.ProfileDataException;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SquadServiceImpl implements SquadService{

    public final SquadRepository squadRepository;

    public final AutenticacaoRepository autenticacaoRepository;

    public final PerfilRepository perfilRepository;

    public final ParticipanteService participanteService;


    public final CategoriaSquadRepository categoriaSquadRepository;

    public final CategoriaSquadMapper categoriaSquadMapper;

    public final ParticipanteRepository participanteRepository;

    public final AutenticacaoService autenticacaoService;

    public final HistoriaRepository historiaRepository;

    public final TarefaRepository tarefaRepository;

    public final SquadMapper squadMapper;

    private static String caminhoImagens = "C://Users/bocsg/OneDrive/Documentos/opensquad/";


    @Override
    public SquadDTO save(String token, SquadDTO squadDTO, String funcaoCriador, Integer cargaHoraria, Integer categoriaId) {
       Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
       if(autenticacao == null){
           throw new AuthDataException("Token não existente");
       }

        Optional<CategoriaSquad> categoriaSquad = categoriaSquadRepository.findById(Long.valueOf(categoriaId));
        categoriaSquad.ifPresent(squad -> squadDTO.setArea(categoriaSquadMapper.map(squad)));

        squadDTO.setStatus("A");
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

        return getSquadDTOSImages(squads);
    }

    public List<SquadDTO> getSquadDTOSImages(List<Squad> squads) {
        List<SquadDTO> squadDTOS = squadMapper.mapToDTO(squads);

        for (SquadDTO squadDTO : squadDTOS) {
            if (squadDTO.getImgSquad() != null && !squadDTO.getImgSquad().isEmpty()) {
                try {
                    File imagemArquivo = new File(caminhoImagens + squadDTO.getImgSquad());
                    squadDTO.setImgSquadfile(Files.readAllBytes(imagemArquivo.toPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return squadDTOS;
    }

    @Override
    public List<SquadDTO> findAll() throws IOException {
            List<Squad> squad = squadRepository.findAll();
            return getSquadDTOSImages(squad);
    }

    @Override
    public List<SquadDTO> findByCategoria(Integer idcategoria, String nomesquad) throws IOException {

        if(idcategoria == null && (nomesquad == null || nomesquad.isEmpty())){
            List<Squad> squads = squadRepository.findAll();
            return getSquadDTOSImages(squads);
        }

        if(idcategoria == null){
            List<Squad> squads = squadRepository.findByNome(nomesquad);
            return getSquadDTOSImages(squads);
        }

        List<Squad> squads = squadRepository.findByAreaIdAndNomeContainingIgnoreCase(Long.valueOf(idcategoria), (nomesquad!=null ? nomesquad : ""));
        return  squadMapper.mapToDTO(squads);
    }

    @Override
    public String salvarFotoSquad(String token, Long idsquad, Long idFoto) {
        //salvando imagem
        Optional<Squad> squad = squadRepository.findById(idsquad);
        if(squad.isPresent()) {
            squad.get().setImgSquad(idFoto.toString());
            squadRepository.save(squad.get());
        }
        return "imagem salva";
    }

    @Override
    public SquadDTO findById(Long id) {
      Optional<Squad> squad = squadRepository.findById(id);
      List<Squad> squads = new ArrayList<>();

      squads.add(squad.get());
        List<SquadDTO> squadDTO = getSquadDTOSImages(squads);
      return squadDTO.get(0);
    }

    @Override
    public SquadDTO deleteById(Long id, String token) {
        RetornoPerfilDTO retornoPerfilDTO = autenticacaoService.verificarPerfil(token);
        Participante participante = participanteRepository.findByIdPerfilIdAndIdSquadId(retornoPerfilDTO.getId(), id);
        if(participante.getStatus().equals("D")){

         List<Historia> historias = historiaRepository.findByIdParticipanteId(participante.getId());


         for(Historia historia: historias) {
             List<Tarefa> tarefas = tarefaRepository.findByIdHistoriaId(historia.getId());
             for (Tarefa tarefa : tarefas) {
                 historiaRepository.deleteById(tarefa.getId());
             }
             historiaRepository.deleteById(historia.getId());
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
        }else {
           throw new ProfileDataException("você não possui status de dono para excluir o squad");
        }
        return null;
    }
}
