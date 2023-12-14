package com.v1.opensquad.mapper;

import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.entity.Convite;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConviteMapper {

    ConviteDTO map(Convite convite);

    Convite map(ConviteDTO conviteDTO);

    List<ConviteDTO> map2(List<Convite> convites);
}
