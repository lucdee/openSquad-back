package com.v1.opensquad.service;

import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.auth.AutenticacaoDTO;
import com.v1.opensquad.dto.auth.AutenticacaoRetornoDTO;

public interface AutenticacaoService {

    AutenticacaoRetornoDTO auth(AutenticacaoDTO autenticacaoDTO);

    RetornoPerfilDTO verificar(String token);

}
