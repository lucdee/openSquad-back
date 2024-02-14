package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class TarefasListStatusDTO {

    private List<TarefaDTO> listaBacklog;
    private List<TarefaDTO> listaEmDesenvolvimento;

    private List<TarefaDTO> listaEmTeste;
    private List<TarefaDTO> listaPronto;

    private List<TarefaDTO> listaArquivado;

    public TarefasListStatusDTO() {
        this.listaBacklog = new ArrayList<>();
        this.listaEmDesenvolvimento = new ArrayList<>();
        this.listaEmTeste = new ArrayList<>();
        this.listaPronto = new ArrayList<>();
        this.listaArquivado = new ArrayList<>();
    }
}