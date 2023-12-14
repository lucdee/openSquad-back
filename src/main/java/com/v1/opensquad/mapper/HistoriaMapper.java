package com.v1.opensquad.mapper;


import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.entity.Convite;
import com.v1.opensquad.entity.Historia;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HistoriaMapper {


    HistoriaDTO map(Historia historia);

    Historia map(HistoriaDTO conviteDTO);

    List<Historia> map2(List<HistoriaDTO> historiaDTOS);


    List<HistoriaDTO> map3(List<Historia> historia);
}
