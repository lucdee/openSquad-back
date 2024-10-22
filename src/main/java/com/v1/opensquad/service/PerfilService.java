package com.v1.opensquad.service;

import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;

public interface PerfilService {

    PerfilDTO save(PerfilDTO perfilDto, Boolean isGoogle);

    PerfilDTO mudarNomeUsuario(String token,String novoNome, Long idPerfil);

    RetornoPerfilDTO findById(Long id);
}
