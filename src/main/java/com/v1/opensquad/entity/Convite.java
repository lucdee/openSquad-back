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
@Table(name = "convite")

public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idperfilenvio")
    private Perfil idPerfilEnvio;

    @ManyToOne
    @JoinColumn(name = "idsquad")
    private Squad idSquad;

    @Column(name = "dataconvite")
    private String dataConvite;

    @Column(name = "status")
    private String status;

    @Column(name = "funcao")
    private String funcao;

    @Column(name = "participacao")
    private String participacao;

    @Column(name = "cargahoraria")
    private Long cargahoraria;
}
