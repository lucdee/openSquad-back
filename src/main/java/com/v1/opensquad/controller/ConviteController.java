package com.v1.opensquad.controller;


import com.v1.opensquad.dto.ConviteDTO;
import com.v1.opensquad.dto.ParticipanteDTO;
import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.service.ConviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/convite")
public class ConviteController {

    private final ConviteService conviteService;

    @PostMapping()
    public ResponseEntity<ConviteDTO> save(@RequestBody ConviteDTO conviteDTO,
                                         @RequestHeader("token") String token,
                                         @RequestParam("usuario") String usuario,
                                         @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(conviteService.save(token, conviteDTO,usuario, idsquad));
    }


    @GetMapping("/buscarporperfil")
    public ResponseEntity<List<ConviteDTO>> convitesPorPerfil(
                                                              @RequestHeader("token") String token
    ) {
        return ResponseEntity.ok(conviteService.convitesPorPerfil(token));
    }

    @PostMapping("/mudarStatus")
    public ResponseEntity<ParticipanteDTO> mudarStatus(
            @RequestHeader("token") String token,
            @RequestParam("idConvite") Integer idConvite,
            @RequestParam("status") String status
    ) {
        return ResponseEntity.ok(conviteService.alterarStatus(token, idConvite, status));
    }

}
