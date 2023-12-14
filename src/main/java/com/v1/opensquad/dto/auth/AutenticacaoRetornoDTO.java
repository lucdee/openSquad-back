package com.v1.opensquad.dto.auth;

import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class AutenticacaoRetornoDTO {

    private String token;

    private RetornoPerfilDTO retornoPerfilDTO;
}
