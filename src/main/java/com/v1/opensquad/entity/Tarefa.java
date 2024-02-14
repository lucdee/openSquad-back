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
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idhistoria")
    private Historia idHistoria;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "iniciodata")
    private String inicioData;

    @Column(name = "fimdata")
    private String fimData;

    @Column(name = "status")
    private Integer status;
}
