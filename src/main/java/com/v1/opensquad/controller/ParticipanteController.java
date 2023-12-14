package com.v1.opensquad.controller;

import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.service.ParticipanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/participante")
public class ParticipanteController {

    private final ParticipanteService participanteService;

    @GetMapping("/findbysquad")
    public ResponseEntity<List<ParticipanteDTO>> findByToken(@RequestParam("idsquad") Long idsquad) {
        return ResponseEntity.ok(participanteService.findBySquad(idsquad));
}

    @GetMapping("/findparticipacao")
    public ResponseEntity<String> findByToken(
            @RequestHeader("token") String token,
            @RequestParam("idsquad") Long idsquad) {
        return ResponseEntity.ok(participanteService.verificarStatusParticipantePorSquad(token, idsquad));
    }

}
