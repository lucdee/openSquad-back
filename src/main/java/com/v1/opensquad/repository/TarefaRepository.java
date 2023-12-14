package com.v1.opensquad.repository;

import com.v1.opensquad.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByIdHistoriaId(Long id);
}
