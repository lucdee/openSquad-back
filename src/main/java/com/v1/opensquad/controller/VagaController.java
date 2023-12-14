package com.v1.opensquad.controller;


import com.v1.opensquad.dto.SquadDTO;
import com.v1.opensquad.dto.VagaDTO;
import com.v1.opensquad.service.VagaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/vaga")
public class VagaController {

    private final VagaService vagaService;

    @PostMapping()
    public ResponseEntity<VagaDTO> save(@RequestBody VagaDTO vagaDTO,
                                         @RequestHeader("token") String token,
                                         @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(vagaService.save(token, vagaDTO, idsquad));
    }


    @GetMapping("/buscarporsquad")
    public ResponseEntity<List<VagaDTO>> findByidsquad(
                                        @RequestParam("idsquad") Long idsquad
    ) {
        return ResponseEntity.ok(vagaService.findBySquad(idsquad));
    }


    @GetMapping("/findbyid")
    public ResponseEntity<VagaDTO> findById(
            @RequestParam("idavaga") Long idavaga
    ) {
        return ResponseEntity.ok(vagaService.findById(idavaga));
    }


}
