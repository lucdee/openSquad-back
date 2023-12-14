package com.v1.opensquad.service;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.CategoriaSquad;
import com.v1.opensquad.mapper.CategoriaSquadMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.CategoriaSquadRepository;
import com.v1.opensquad.repository.SquadRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaSquadServiceImpl implements CategoriaSquadService{

    private final CategoriaSquadRepository categoriaSquadRepository;

    private final CategoriaSquadMapper categoriaSquadMapper;

    private final AutenticacaoRepository autenticacaoRepository;

    @Override
    public CategoriaSquadDTO save(String token, CategoriaSquadDTO categoriaSquadDTO) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token Inválido!");
        }
        CategoriaSquad categoriaSquad = categoriaSquadRepository.save(categoriaSquadMapper.map(categoriaSquadDTO));
        return categoriaSquadMapper.map(categoriaSquad);
    }

    @Override
    public List<CategoriaSquadDTO> findAll() {
       List<CategoriaSquad> categoriaSquads = categoriaSquadRepository.findAll();
        return categoriaSquadMapper.map2(categoriaSquads);
    }
}
