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
@Table(name = "curtidasquad")
public class CurtidaSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "idperfil")
    private Long idPerfil;

    @Column(name = "idsquad")
    private Long idSquad;
}
