package com.v1.opensquad.controller;


import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.contador.ContadorResponseDTO;
import com.v1.opensquad.service.contador.ContadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/contador")
public class ContatorController {

    private final ContadorService contadorService;

    @GetMapping("/contar")
    public ResponseEntity<ContadorResponseDTO> contar() {
        return ResponseEntity.ok(contadorService.contar());
    }

}
