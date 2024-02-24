package com.v1.opensquad.controller;


import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;
import com.v1.opensquad.dto.TarefasListStatusDTO;
import com.v1.opensquad.service.HistoriaService;
import com.v1.opensquad.service.TarefaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/tarefa")
public class TarefaController {
    private final TarefaService tarefaService;

    @PostMapping()
    public ResponseEntity<TarefaDTO> save(@RequestBody TarefaDTO tarefaDTO,
                                            @RequestHeader("Authorization") String token,
                                            @RequestParam("idhistoria") Long idhistoria
    ) {
        return ResponseEntity.ok(tarefaService.save(token, idhistoria, tarefaDTO));
    }


    @GetMapping("/findbyhistoria")
    public ResponseEntity<List<TarefaDTO>> findByHistoria(
                                          @RequestParam("idhistoria") Long idhistoria
    ) {
        return ResponseEntity.ok(tarefaService.findByHistoria( idhistoria));
    }

    @GetMapping()
    public ResponseEntity<TarefasListStatusDTO> findBySquad(
            @RequestParam("idsquad") Long idSquad
    ) {
        return ResponseEntity.ok(tarefaService.findAll(idSquad));
    }

    @PutMapping("/avancar-status")
    public ResponseEntity<TarefaDTO> avancarStatus(
            @RequestHeader("Authorization") String token,
            @RequestParam("idtarefa") Long idtarefa
    ) {
        return ResponseEntity.ok(tarefaService.avancarStatus( token, idtarefa));
    }

    @PostMapping("/adicionar-assignee")
    public ResponseEntity<TarefaDTO> adicionarAssigneeHistoria(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "idtarefa") Long idtarefa,
            @RequestParam(value = "idParticipante") Long idParticipante,
            @RequestParam(value = "idSquad") Long idSquad
    ) {
        return ResponseEntity.ok(tarefaService.adicionarAssigneeTarefa(token,idtarefa, idParticipante, idSquad));
    }
}
