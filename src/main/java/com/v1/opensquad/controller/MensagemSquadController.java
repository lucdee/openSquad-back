package com.v1.opensquad.controller;

import com.v1.opensquad.dto.HistoriaDTO;
import com.v1.opensquad.dto.MensagemSquadDTO;
import com.v1.opensquad.service.MensagemSquadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mensagemsquad")
public class MensagemSquadController {

    private final MensagemSquadService mensagemSquadService;

    @PostMapping()
    public ResponseEntity<MensagemSquadDTO> save(@RequestBody MensagemSquadDTO mensagemSquadDTO,
                                                 @RequestHeader("Authorization") String token,
                                                 @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(mensagemSquadService.save(token, idsquad, mensagemSquadDTO));
    }

    @GetMapping()
    public ResponseEntity<List<MensagemSquadDTO>> findBySquad(
                                                 @RequestHeader("Authorization") String token,
                                                 @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(mensagemSquadService.findBySquad(token, idsquad));
    }

}
