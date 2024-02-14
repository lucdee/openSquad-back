package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.v1.opensquad.entity.Participante;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class HistoriaDTO {

    private Long id;
    private Participante idParticipante;
    private String nome;
    private String descricao;
    private String finalData;
    private String inicialData;
    private Integer status;
}
