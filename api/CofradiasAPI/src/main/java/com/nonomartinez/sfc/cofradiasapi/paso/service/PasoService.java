package com.nonomartinez.sfc.cofradiasapi.paso.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
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
    private final HermandadRepository hermandadRepository;

    public GetPasoDTO getPaso(UUID id){
        Optional<Paso> optionalPaso = pasoRepository.findById(id);
        if(optionalPaso.isEmpty())
            throw new NotFoundException("");

        return GetPasoDTO.of(optionalPaso.get());
    }

    public PostPasoDTO addPaso(PostPasoDTO nuevo, UUID hermandadId){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(hermandadId);
        if(hermandadOptional.isEmpty())
            throw new NotFoundException("No existe la hermandad");

        Hermandad hermandad = hermandadOptional.get();
        Paso paso = Paso.builder()
                .imagen(nuevo.imagen())
                .capataz(nuevo.capataz())
                .numCostaleros(nuevo.numCostaleros())
                .hermandad(hermandad)
                .build();

        pasoRepository.save(paso);

        return nuevo;
    }
}
