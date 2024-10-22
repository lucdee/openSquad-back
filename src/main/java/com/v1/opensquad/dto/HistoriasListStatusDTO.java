package com.v1.opensquad.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class HistoriasListStatusDTO {

    private List<HistoriaDTO> listaBacklog;
    private List<HistoriaDTO> listaEmDesenvolvimento;

    private List<HistoriaDTO> listaEmTeste;
    private List<HistoriaDTO> listaPronto;

    private List<HistoriaDTO> listaArquivado;

    public HistoriasListStatusDTO() {
        this.listaBacklog = new ArrayList<>();
        this.listaEmDesenvolvimento = new ArrayList<>();
        this.listaEmTeste = new ArrayList<>();
        this.listaPronto = new ArrayList<>();
        this.listaArquivado = new ArrayList<>();
    }
}