package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Participante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriaRepository  extends JpaRepository<Historia, Long> {

    List<Historia> findByIdParticipanteId(Long idparticipante);
}
