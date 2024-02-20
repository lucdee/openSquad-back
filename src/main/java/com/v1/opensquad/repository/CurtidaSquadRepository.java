package com.v1.opensquad.repository;

import com.v1.opensquad.entity.CurtidaSquad;
import com.v1.opensquad.entity.Historia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurtidaSquadRepository  extends JpaRepository<CurtidaSquad, Long> {


    CurtidaSquad findByIdSquadAndAndIdPerfil(Long idSquad, Long IdPerfil);

    List<CurtidaSquad> findByIdSquad(Long idSquad);
}
