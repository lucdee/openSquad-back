package com.v1.opensquad.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.v1.opensquad.entity.Participante;
import com.v1.opensquad.entity.Squad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class MensagemSquadDTO {


    private Long id;
    private String mensagem;
    private Participante participante;
    private LocalDateTime dataEnvio;
    private Squad squad;
}
