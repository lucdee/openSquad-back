package com.v1.opensquad.controller;

import com.v1.opensquad.dto.RetornoPerfilDTO;
import com.v1.opensquad.dto.auth.AutenticacaoDTO;
import com.v1.opensquad.dto.auth.AutenticacaoRetornoDTO;
import com.v1.opensquad.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthController {

    private final AutenticacaoService autenticacaoService;

    @PostMapping()
    public ResponseEntity<AutenticacaoRetornoDTO> auth(@RequestBody AutenticacaoDTO autenticacaoDTO) {
        return ResponseEntity.ok(autenticacaoService.auth(autenticacaoDTO));
    }

    @GetMapping("/verificar")
    public ResponseEntity<RetornoPerfilDTO> save(@RequestParam("token") String token) {
        return ResponseEntity.ok(autenticacaoService.verificar(token));
    }
}
