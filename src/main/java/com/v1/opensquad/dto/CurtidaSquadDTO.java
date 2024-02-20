package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class CurtidaSquadDTO {

    private Long id;
    private Long idPerfil;
    private Long idSquad;
}
