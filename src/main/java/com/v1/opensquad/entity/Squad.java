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
@Table(name = "squad")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "imgsquad")
    private String imgSquad;

    @ManyToOne
    @JoinColumn(name = "area")
    private CategoriaSquad area;

    @Column(name = "datacriacao")
    private String dataCriacao;

    @Column(name = "status")
    private String status;
}
