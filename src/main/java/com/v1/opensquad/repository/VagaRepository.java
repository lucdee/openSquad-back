package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Squad;
import com.v1.opensquad.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VagaRepository  extends JpaRepository<Vaga, Long> {

    List<Vaga> findByIdsquadId(Long idsquad);
}
