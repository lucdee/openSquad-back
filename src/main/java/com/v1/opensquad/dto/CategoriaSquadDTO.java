package com.v1.opensquad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class CategoriaSquadDTO {


    @JsonProperty("value")
    private Integer id;

    @JsonProperty("label")
    private String descricao;
}
