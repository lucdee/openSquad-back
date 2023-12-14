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
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nomecompleto")
    private String nomeCompleto;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "datanascimento")
    private String dataNascimento;

    @Column(name = "senha")
    private String senha;

    @Column(name = "level")
    private Integer level;

    @Column(name = "exp")
    private Integer exp;

    @Column(name = "imgperfil")
    private String imgPerfil;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "datacriacao")
    private String dataCriacao;

    @Column(name = "premium")
    private String premium;

    @Column(name = "status")
    private String status;
}
