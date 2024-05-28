package com.nonomartinez.sfc.cofradiasapi.paso.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import com.nonomartinez.sfc.cofradiasapi.paso.repository.PasoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasoService {

    private final PasoRepository pasoRepository;

    public GetPasoDTO getPaso(UUID id){
        Optional<Paso> optionalPaso = pasoRepository.findById(id);
        if(optionalPaso.isEmpty())
            throw new NotFoundException("");

        return GetPasoDTO.of(optionalPaso.get());
    }
}
