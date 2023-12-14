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
@Table(name = "autenticacao")
public class Autenticacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idperfil")
    private Perfil idPerfil;

    @Column(name = "token")
    private String token;

    @Column(name = "datacriacao")
    private String dataCriacao;

    @Column(name = "dataexpiracao")
    private String dataExpiracao;

}
