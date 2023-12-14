package com.v1.opensquad.mapper;


import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;
import com.v1.opensquad.entity.Historia;
import com.v1.opensquad.entity.Tarefa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaDTO map(Tarefa tarefa);

    Tarefa map(TarefaDTO TarefaDTO);

    List<Tarefa> map2(List<TarefaDTO> tarefaDTOS);

    List<TarefaDTO> map3(List<Tarefa> tarefa);
}
