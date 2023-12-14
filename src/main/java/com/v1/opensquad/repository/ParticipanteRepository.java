package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    List<Participante> findByIdPerfilId(Long idPerfil);

    Participante findByIdPerfilIdAndIdSquadId(Long idPerfil, Long idsquad);

    List<Participante> findByIdSquadId(Long idsquad);
}
