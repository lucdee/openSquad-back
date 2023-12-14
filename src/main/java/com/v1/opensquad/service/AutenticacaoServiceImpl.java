package com.v1.opensquad.service;


import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.auth.AutenticacaoDTO;
import com.v1.opensquad.dto.auth.AutenticacaoRetornoDTO;
import com.v1.opensquad.entity.Autenticacao;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.mapper.PerfilMapper;
import com.v1.opensquad.repository.AutenticacaoRepository;
import com.v1.opensquad.repository.PerfilRepository;
import com.v1.opensquad.service.exception.AuthDataException;
import com.v1.opensquad.service.exception.ProfileDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService{

    private final AutenticacaoRepository autenticacaoRepository;

    private final PerfilRepository perfilRepository;

    private final PerfilMapper perfilMapper;

    @Override
    public AutenticacaoRetornoDTO auth(AutenticacaoDTO autenticacaoDTO) {
        List<Perfil> perfilByEmail = perfilRepository.findByEmail(autenticacaoDTO.getEmailUsuario());
        List<Perfil> perfilByUsuario = perfilRepository.findByUsuario(autenticacaoDTO.getEmailUsuario());
        Perfil perfilEncontrado = null;

        if(perfilByUsuario.size() == 0 && perfilByEmail != null){
            perfilEncontrado = perfilByEmail.get(0);
        }else if(perfilByUsuario != null && perfilByEmail.size() == 0){
            perfilEncontrado = perfilByUsuario.get(0);
        }else {
            throw new ProfileDataException("Perfil não encontrado com o email ou usuario");
        }
        if(!perfilEncontrado.getSenha().equals(autenticacaoDTO.getSenha())){
            throw new ProfileDataException("Senha Incorreta");
        }


        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setDataCriacao(String.valueOf(LocalDateTime.now()));
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        autenticacao.setToken(myRandom.substring(0,30));
        autenticacao.setIdPerfil(perfilEncontrado);

        Autenticacao autenticacao1 = autenticacaoRepository.findByIdPerfilId(perfilEncontrado.getId());
        AutenticacaoRetornoDTO autenticacaoRetornoDTO = new AutenticacaoRetornoDTO();
        autenticacaoRetornoDTO.setRetornoPerfilDTO(perfilMapper.mapRetorno(perfilEncontrado));
        if(autenticacao1 != null){
            autenticacaoRetornoDTO.setToken(autenticacao1.getToken());
            return autenticacaoRetornoDTO;
        }
        try {
            autenticacaoRepository.save(autenticacao);
            autenticacaoRetornoDTO.setToken(myRandom.substring(0,30));
            return autenticacaoRetornoDTO;
        }catch (Exception e){
            throw new AuthDataException("Erro ao salvar token");
        }
    }

    @Override
    public RetornoPerfilDTO verificar(String token) {
        Autenticacao autenticacao = autenticacaoRepository.findByToken(token);
        if(autenticacao == null){
            throw new AuthDataException("Token não encontrado");
        }
        Optional<Perfil> perfil = perfilRepository.findById(autenticacao.getIdPerfil().getId());
        if(perfil.isPresent()){
          RetornoPerfilDTO perfilDTO = perfilMapper.mapRetorno(perfil.get());
            return perfilDTO;
        }
        throw new AuthDataException("Perfil não encontrado porém o token é válido");
    }
}
