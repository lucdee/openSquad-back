package com.v1.opensquad.service;


import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.mapper.PerfilMapper;
import com.v1.opensquad.repository.PerfilRepository;
import com.v1.opensquad.service.exception.ProfileDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PerfilServiceImpl implements PerfilService{

    public final PerfilRepository perfilRepository;

    public final PerfilMapper perfilMapper;


    @Override
    public PerfilDTO save(PerfilDTO perfilDto) {
        List<Perfil> perfil = perfilRepository.findByEmail(perfilDto.getEmail());
        if(perfil.size()!= 0 ){
            throw new ProfileDataException("Já existe uma conta com esse email");
        }
        List<Perfil> perfil1 = perfilRepository.findByUsuario(perfilDto.getUsuario());
        if(perfil1.size()!= 0){
            throw new ProfileDataException("Já existe uma conta com esse usuário");
        }
        perfilDto.setLevel(0);
        perfilDto.setExp(0);
        perfilDto.setDataCriacao(String.valueOf(LocalDateTime.now()));
        perfilDto.setPremium("S");
        perfilDto.setStatus("A");

       Perfil perfilSave =  perfilRepository.save(perfilMapper.map(perfilDto));
        return perfilMapper.map(perfilSave);
    }
}
