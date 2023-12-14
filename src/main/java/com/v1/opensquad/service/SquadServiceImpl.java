package com.v1.opensquad.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.CategoriaSquad;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import com.v1.opensquad.mapper.CategoriaSquadMapper;
import com.v1.opensquad.mapper.SquadMapper;
import com.v1.opensquad.repository.*;
import com.v1.opensquad.service.exception.AuthDataException;
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

    public final SquadMapper squadMapper;

    private static String caminhoImagens = "C://Users/bocsg/OneDrive/Documentos/opensquad/";


    @Override
    public SquadDTO save(String token, SquadDTO squadDTO, String funcaoCriador, Integer cargaHoraria, Integer categoriaId) {
       Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
       if(autenticacao == null){
           throw new AuthDataException("Token não existente");
       }

        Optional<CategoriaSquad> categoriaSquad = categoriaSquadRepository.findById(categoriaId);
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

        List<Squad> squads = squadRepository.findByAreaIdAndNomeContainingIgnoreCase(Math.toIntExact(idcategoria), (nomesquad!=null ? nomesquad : ""));
        return getSquadDTOSImages(squads);

    }

    @Override
    public String salvarFotoSquad(String token, Long idsquad, MultipartFile file) {
        //salvando imagem
        try{
            if(file != null){
                byte[] bytes = file.getBytes();
                Path caminho = Paths.get(caminhoImagens+  + idsquad + file.getOriginalFilename());
                Files.write((caminho), bytes);

                Optional<Squad> squad = squadRepository.findById(idsquad);

                if(squad.isPresent()){
                    squad.get().setImgSquad(  idsquad + file.getOriginalFilename() );
                    squadRepository.saveAndFlush(squad.get());
                }
                return "imagem Salva";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Erro ao salvar imagem";
    }

    @Override
    public SquadDTO findById(Long id) {
      Optional<Squad> squad = squadRepository.findById(id);
      List<Squad> squads = new ArrayList<>();

      squads.add(squad.get());
        List<SquadDTO> squadDTO = getSquadDTOSImages(squads);
      return squadDTO.get(0);
    }
}
