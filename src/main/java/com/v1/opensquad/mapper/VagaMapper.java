package com.v1.opensquad.mapper;


import com.v1.opensquad.dto.VagaDTO;
import com.v1.opensquad.entity.Vaga;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VagaMapper {


    Vaga map(VagaDTO vagaDTO);

    VagaDTO map(Vaga vaga);

    List<VagaDTO> map2(List<Vaga> vagas);

    List<Vaga> map3(List<VagaDTO> vagas);
}
