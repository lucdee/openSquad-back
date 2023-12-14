package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Autenticacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Long> {

    Autenticacao findByIdPerfilId(Long id);

    Autenticacao findByToken(String token);
}
