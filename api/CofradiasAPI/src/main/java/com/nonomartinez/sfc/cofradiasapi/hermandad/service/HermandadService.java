package com.nonomartinez.sfc.cofradiasapi.hermandad.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.controller.HermandadController;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HermandadService {

    private final HermandadRepository hermandadRepository;

    public List<GetHermandadDTO> getHermandadPorDia(Dias dias){
        return hermandadRepository.findHermandadByDia(dias).stream().map(GetHermandadDTO::of).toList();
    }

    public GetHermandadDTO getHermandad(UUID id){
        Optional<Hermandad> optionalHermandad = hermandadRepository.findById(id);
        if(optionalHermandad.isEmpty())
            throw new NotFoundException("");

        return GetHermandadDTO.of(optionalHermandad.get());
    }
}
