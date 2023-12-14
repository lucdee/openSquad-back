package com.v1.opensquad.controller;


import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.HistoriasListStatusDTO;
import com.v1.opensquad.service.HistoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/historia")
public class HistoriaController {

    private final HistoriaService historiaService;

    @PostMapping()
    public ResponseEntity<HistoriaDTO> save(@RequestBody HistoriaDTO historiaDTO,
                                           @RequestHeader("token") String token,
                                           @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(historiaService.save(idsquad, token, historiaDTO));
    }

    @GetMapping()
    public ResponseEntity<HistoriasListStatusDTO> findHistoriasBySquad(
                                            @RequestParam("idsquad") Long idsquad,
                                            @RequestParam(value = "idparticipante", required = false) Long idparticipante
    ) {
        return ResponseEntity.ok(historiaService.findHistoriasBySquad(idsquad, idparticipante));
    }


}
