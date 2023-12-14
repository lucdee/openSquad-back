package com.v1.opensquad.controller;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.service.CategoriaSquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/categoriasquad")
public class CategoriaSquadController {

    private final CategoriaSquadService squadService;

    @GetMapping()
    public ResponseEntity<List<CategoriaSquadDTO>> findAll() {
        return ResponseEntity.ok(squadService.findAll());
    }

    @PostMapping()
    public ResponseEntity<CategoriaSquadDTO> save(
            @RequestHeader("token") String token,
            @RequestBody CategoriaSquadDTO categoriaSquadDTO) {
        return ResponseEntity.ok(squadService.save(token, categoriaSquadDTO));
    }
}
