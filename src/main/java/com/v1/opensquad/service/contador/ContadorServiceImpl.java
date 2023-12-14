package com.v1.opensquad.service.contador;


import com.v1.opensquad.dto.contador.ContadorResponseDTO;
import com.v1.opensquad.repository.PerfilRepository;
import com.v1.opensquad.repository.SquadRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContadorServiceImpl implements ContadorService{

    private final SquadRepository squadRepository;

    private final PerfilRepository perfilRepository;

    @Override
    public ContadorResponseDTO contar() {
        Long squads = Long.valueOf(squadRepository.findAll().size());
        Long perfis = Long.valueOf(perfilRepository.findAll().size());
        ContadorResponseDTO contadorResponseDTO = new ContadorResponseDTO();
        contadorResponseDTO.setContas(perfis);
        contadorResponseDTO.setSquads(squads);
        return contadorResponseDTO;
    }
}
