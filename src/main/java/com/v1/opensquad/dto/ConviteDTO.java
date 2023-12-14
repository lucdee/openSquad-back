package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.v1.opensquad.entity.Perfil;
import com.v1.opensquad.entity.Squad;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ConviteDTO {


    private Integer id;
    private PerfilDTO idPerfilEnvio;
    private SquadDTO idSquad;
    private String dataConvite;
    private String status;
    private String funcao;
    private String participacao;
    private Long cargahoraria;
}
