package com.v1.opensquad.service;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.dto.SquadDTO;

import java.util.List;

public interface CategoriaSquadService {

    CategoriaSquadDTO save(String token, CategoriaSquadDTO categoriaSquadDTO);

    List<CategoriaSquadDTO> findAll();
}
