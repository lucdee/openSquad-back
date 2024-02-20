package com.v1.opensquad.controller;

import com.v1.opensquad.dto.CategoriaSquadDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.service.SquadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/squad")
public class SquadController {
    private final SquadService squadService;

    @PostMapping()
    public ResponseEntity<SquadDTO> save(@RequestBody SquadDTO squadDTO,
                                         @RequestHeader("Authorization") String token,
                                         @RequestParam("funcao-criador") String funcaoCriador,
                                         @RequestParam("carga-horaria") Integer cargaHoraria,
                                          @RequestParam("categoriaid") Integer categoriaId
                                         ) {
        return ResponseEntity.ok(squadService.save(token, squadDTO, funcaoCriador, cargaHoraria, categoriaId));
    }


    @PostMapping("/salvarFoto")
    public ResponseEntity<String> salvarFotoSquad(
                                         @RequestHeader("token") String token,
                                         @RequestParam("idsquad") Long idsquad,
                                         @RequestParam("idFoto") String urlFoto
    ) {
        return ResponseEntity.ok(squadService.salvarFotoSquad(token, idsquad, urlFoto));
    }

    @GetMapping("/findByToken")
    public ResponseEntity<List<SquadDTO>> findByToken(@RequestHeader("token") String token) throws IOException {
        return ResponseEntity.ok(squadService.findByToken(token));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<SquadDTO>> findAll() throws IOException {
        return ResponseEntity.ok(squadService.findAll());
    }

    @GetMapping("/findbyid")
    public ResponseEntity<SquadDTO> findById(@RequestParam("idsquad") Long id) throws IOException {
        return ResponseEntity.ok(squadService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SquadDTO> deleteById(@RequestHeader("Authorization") String token,@PathVariable("id") Long id) throws IOException {
        return ResponseEntity.ok(squadService.deleteById(id, token));
    }

    @GetMapping("/findbyfilters")
    public ResponseEntity<List<SquadDTO>> findByCategoria(
            @RequestParam(value = "nomesquad", required = false) String nomesquad,
    @RequestParam(value ="idcategoria", required = false) Integer idcategoria) throws IOException {
        return ResponseEntity.ok(squadService.findByCategoria(idcategoria, nomesquad));
    }

    @PostMapping("/curtir")
    public ResponseEntity<String> findByCategoria(
            @RequestParam(value = "idsquad", required = false) Long idsquad,
            @RequestHeader("Authorization") String token) throws IOException {
        return ResponseEntity.ok(squadService.curtirSquad(idsquad, token));
    }
}
