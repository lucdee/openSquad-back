package com.v1.opensquad.controller;


import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.HistoriasListStatusDTO;
import com.v1.opensquad.dto.TarefaDTO;
import com.v1.opensquad.service.HistoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.annotation.XmlElementDecl;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/historia")
public class HistoriaController {

    private final HistoriaService historiaService;

    @PostMapping()
    public ResponseEntity<HistoriaDTO> save(@RequestBody HistoriaDTO historiaDTO,
                                           @RequestHeader("Authorization") String token,
                                           @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(historiaService.save(idsquad, token, historiaDTO));
    }

    @PutMapping("/avancar-status")
    public ResponseEntity<HistoriaDTO> avancarStatus(
                                            @RequestHeader("Authorization") String token,
                                            @RequestParam("idhistoria") Long idhistoria
    ) {
        return ResponseEntity.ok(historiaService.avancarStatus( token, idhistoria));
    }

    @GetMapping()
    public ResponseEntity<HistoriasListStatusDTO> findHistoriasBySquad(
                                            @RequestParam("idsquad") Long idsquad,
                                            @RequestParam(value = "idparticipante", required = false) Long idparticipante
    ) {
        return ResponseEntity.ok(historiaService.findHistoriasBySquad(idsquad, idparticipante));
    }

    @GetMapping("/ativas")
    public ResponseEntity<List<HistoriaDTO>> findHistoriasBySquad(
            @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(historiaService.findAllAtivas(idsquad));
    }


    @PostMapping("/adicionar-assignee")
    public ResponseEntity<HistoriaDTO> adicionarAssigneeHistoria(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "idhistoria") Long idhistoria,
            @RequestParam(value = "idParticipante") Long idParticipante,
            @RequestParam(value = "idSquad") Long idSquad
    ) {
        return ResponseEntity.ok(historiaService.adicionarAssigneeHistoria(token,idhistoria, idParticipante, idSquad));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteById(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "idhistoria") Long idhistoria,
            @RequestParam(value = "idSquad") Long idSquad
    ) {
        return ResponseEntity.ok(historiaService.deleteById(token,idhistoria, idSquad));
    }

    @PutMapping("/edit")
    public ResponseEntity<HistoriaDTO> editById(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "idhistoria") Long idhistoria,
            @RequestParam(value = "idSquad") Long idSquad,
            @RequestBody HistoriaDTO historiaDTO
    ) {
        return ResponseEntity.ok(historiaService.edit(token,idhistoria, idSquad, historiaDTO));
    }
}
