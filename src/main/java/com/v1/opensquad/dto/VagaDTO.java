package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class VagaDTO {

    private Long id;
    private Participante idpublicador;
    private Squad idsquad;
    private String descricao;
    private String nome;
    private String funcao;
    private String participacao;
    private String status;
    private String atividades;
    private Long cargahoraria;
}
