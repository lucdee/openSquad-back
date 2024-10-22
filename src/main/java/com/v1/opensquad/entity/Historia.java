package com.v1.opensquad.entity;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "historia")
public class Historia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idparticipantereporter")
    private Participante idParticipanteReporter;

    @ManyToOne
    @JoinColumn(name = "idparticipanteassignee")
    private Participante idParticipanteAssignee;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "finaldata")
    private String finalData;

    @Column(name = "inicialdata")
    private String inicialData;

    @Column(name = "status")
    private Integer status;

}
