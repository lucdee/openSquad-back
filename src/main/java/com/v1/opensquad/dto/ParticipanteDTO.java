package com.v1.opensquad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ParticipanteDTO {

    private Long id;
    private Integer participacao;
    private String funcao;
    private SquadDTO idSquad;
    private PerfilDTO idPerfil;
    private String dataEntrada;
    private String status;
    private Integer cargaHoraria;
}
