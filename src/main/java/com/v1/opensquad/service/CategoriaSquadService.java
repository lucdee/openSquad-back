package com.v1.opensquad.service;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.dto.SquadDTO;

import java.util.List;

public interface CategoriaSquadService {

    CategoriaSquadDTO save( CategoriaSquadDTO categoriaSquadDTO);

    List<CategoriaSquadDTO> findAll();

    CategoriaSquadDTO deleteById(Long idCategoria);
}
