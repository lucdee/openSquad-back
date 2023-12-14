package com.v1.opensquad.controller;

import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.service.PerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/perfil")
public class PerfilController {

    private final PerfilService perfilService;

    @PostMapping()
    public ResponseEntity<PerfilDTO> save(@RequestBody PerfilDTO perfilDto) {
        return ResponseEntity.ok(perfilService.save(perfilDto));
    }

}
