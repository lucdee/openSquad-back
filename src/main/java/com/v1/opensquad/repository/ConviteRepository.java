package com.v1.opensquad.repository;

import com.v1.opensquad.entity.CategoriaSquad;
import com.v1.opensquad.entity.Convite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConviteRepository extends JpaRepository<Convite, Integer> {

    List<Convite> findByIdPerfilEnvioId(Long idPerfilEnvio);
}
