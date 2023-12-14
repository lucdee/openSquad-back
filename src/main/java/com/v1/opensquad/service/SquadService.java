package com.v1.opensquad.service;

import com.v1.opensquad.dto.RetornoImagemDTO;
import com.v1.opensquad.dto.SquadDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SquadService {

    SquadDTO save(String token, SquadDTO squadDTO, String funcaoCriador, Integer cargaHoraria, Integer categoriaId);

    List<SquadDTO> findByToken(String token) throws IOException;

    List<SquadDTO> findAll() throws IOException;

    List<SquadDTO> findByCategoria(Integer idcategoria, String nomesquad) throws IOException;
    String salvarFotoSquad(String token,Long idsquad,MultipartFile file);

    SquadDTO findById(Long id);
}
