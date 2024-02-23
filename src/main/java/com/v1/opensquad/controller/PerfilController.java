package com.v1.opensquad.controller;

import com.v1.opensquad.dto.PerfilDTO;
import com.v1.opensquad.dto.RetornoPerfilDTO;
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
    public ResponseEntity<PerfilDTO> save(@RequestBody PerfilDTO perfilDto,
                                          @RequestParam("isGoogle") Boolean isGoogle
                                          ) {
        return ResponseEntity.ok(perfilService.save(perfilDto, isGoogle));
    }

    @PostMapping("/mudarnome")
    public ResponseEntity<PerfilDTO> mudarnome(
                                          @RequestHeader("Authorization") String token,
                                          @RequestParam("idperfil") Long idperfil,
                                               @RequestParam("novonome") String novonome
    ) {
        return ResponseEntity.ok(perfilService.mudarNomeUsuario(token,novonome, idperfil));
    }

    @GetMapping("/findbyid/{idperfil}")
    public ResponseEntity<RetornoPerfilDTO> mudarnome(
            @PathVariable("idperfil") Long idperfil
    ) {
        return ResponseEntity.ok(perfilService.findById(idperfil));
    }


}
