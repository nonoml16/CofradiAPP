package com.nonomartinez.sfc.cofradiasapi.hermandad.service;

import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HermandadService {

    private final HermandadRepository hermandadRepository;

    public List<GetHermandadDTO> getHermandadPorDia(Dias dias){
        return hermandadRepository.findHermandadByDia(dias).stream().map(GetHermandadDTO::of).toList();
    }
}
