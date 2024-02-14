package com.v1.opensquad.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensagemsquad")
public class MensagemSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;


    @Column(name = "mensagem")
    private String mensagem;

    @ManyToOne
    @JoinColumn(name = "idparticipante")
    private Participante participante;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    @ManyToOne
    @JoinColumn(name = "idsquad")
    private Squad squad;
}
