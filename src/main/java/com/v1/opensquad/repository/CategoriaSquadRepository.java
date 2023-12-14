package com.v1.opensquad.repository;

import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.entity.CategoriaSquad;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaSquadRepository extends JpaRepository<CategoriaSquad, Integer> {


}
