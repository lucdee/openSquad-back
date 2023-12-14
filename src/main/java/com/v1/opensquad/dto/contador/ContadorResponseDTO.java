package com.v1.opensquad.dto.contador;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class ContadorResponseDTO {

    private Long contas;
    private Long squads;
}
