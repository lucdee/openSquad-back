package com.v1.opensquad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RetornoPerfilDTO {

    private Long id;
    private String email;
    private String usuario;
    private Integer level;
    private Integer exp;
    private String imgPerfil;
    private String descricao;
    private String dataCriacao;
    private String premium;
    private String status;

}
