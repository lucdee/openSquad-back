package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.v1.opensquad.entity.Historia;
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
public class TarefaDTO {


    private Long id;
    private Historia idHistoria;
    private String nome;
    private String descricao;
    private String inicioData;
    private String fimData;
    private String status;
}
