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
@Table(name = "publicacaovaga")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idpublicador")
    private Participante idpublicador;

    @ManyToOne
    @JoinColumn(name = "idsquad")
    private Squad idsquad;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "nome")
    private String nome;

    @Column(name = "funcao")
    private String funcao;

    @Column(name = "participacao")
    private String participacao;

    @Column(name = "status")
    private String status;

    @Column(name = "atividades")
    private String atividades;

    @Column(name = "cargahoraria")
    private Long cargahoraria;
}
