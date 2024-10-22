package com.v1.opensquad.service;


import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.mapper.PerfilMapper;
import com.v1.opensquad.repository.PerfilRepository;
import com.v1.opensquad.service.exception.ProfileDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PerfilServiceImpl implements PerfilService{

    public final PerfilRepository perfilRepository;

    public final PerfilMapper perfilMapper;

    public final AutenticacaoService autenticacaoService;


    @Override
    public PerfilDTO save(PerfilDTO perfilDto, Boolean isGoogle) {

        if(!isGoogle){
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
        }else {
            List<Perfil> perfil =   perfilRepository.findByEmail(perfilDto.getEmail());
            if(perfil.size() != 0){
                return perfilMapper.map(perfil.get(0));
            }else {
                perfilDto.setLevel(0);
                perfilDto.setExp(0);
                perfilDto.setDataCriacao(String.valueOf(LocalDateTime.now()));
                perfilDto.setPremium("S");
                perfilDto.setStatus("A");

                Perfil perfilSave =  perfilRepository.save(perfilMapper.map(perfilDto));
                return perfilMapper.map(perfilSave);
            }
        }

    }

    @Override
    public PerfilDTO mudarNomeUsuario(String token,String novoNome, Long idPerfil) {
        RetornoPerfilDTO retornoPerfilDTO = autenticacaoService.verificarPerfil(token);

       Optional<Perfil> perfil = perfilRepository.findById(idPerfil);
       if(perfil.isPresent()){
           if(retornoPerfilDTO.getUsuario().equals(perfil.get().getUsuario())){
               perfil.get().setUsuario(novoNome);
               Perfil perfil1 = perfilRepository.save(perfil.get());
               PerfilDTO perfilDTO = perfilMapper.map(perfil1);
               return perfilDTO;
           }
         }
        throw new ProfileDataException("Perfil inexistente");
    }

    @Override
    public RetornoPerfilDTO findById(Long id) {
        Optional<Perfil> perfil =   perfilRepository.findById(id);
        if(perfil.isPresent()){
            return perfilMapper.mapRetorno(perfil.get());
        }
        return null;
    }
}
