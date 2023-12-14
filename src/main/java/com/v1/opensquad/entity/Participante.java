package com.v1.opensquad.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "participante")
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idperfil")
    private Perfil idPerfil;

    @Column(name = "participacao")
    private Integer participacao;

    @Column(name = "funcao")
    private String funcao;

    @ManyToOne
    @JoinColumn(name = "idsquad")
    private Squad idSquad;

    @Column(name = "dataentrada")
    private String dataEntrada;

    @Column(name = "status")
    private String status;

    @Column(name = "cargahoraria")
    private Integer cargaHoraria;
}
