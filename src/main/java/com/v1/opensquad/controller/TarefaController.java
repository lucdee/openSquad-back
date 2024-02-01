package com.v1.opensquad.controller;


import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.TarefaDTO;
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


    @GetMapping()
    public ResponseEntity<List<TarefaDTO>> findByHistoria(
                                          @RequestParam("idhistoria") Long idhistoria
    ) {
        return ResponseEntity.ok(tarefaService.findByHistoria( idhistoria));
    }

}
