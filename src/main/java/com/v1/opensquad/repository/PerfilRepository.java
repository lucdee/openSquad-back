package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    List<Perfil> findByEmail(String emailUsuario);

    List<Perfil> findByUsuario(String emailUsuario);
}
