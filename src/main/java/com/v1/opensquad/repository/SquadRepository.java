package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SquadRepository extends JpaRepository<Squad, Long> {

    List<Squad> findByAreaIdAndNomeContainingIgnoreCase(Integer idcategoria, String nome);

    List<Squad> findByNome(String nome);

}
