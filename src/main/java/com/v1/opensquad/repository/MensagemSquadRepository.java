package com.v1.opensquad.repository;

import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.MensagemSquad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemSquadRepository  extends JpaRepository<MensagemSquad, Long> {

    List<MensagemSquad> findByParticipanteId(Long idParticipante);

}
