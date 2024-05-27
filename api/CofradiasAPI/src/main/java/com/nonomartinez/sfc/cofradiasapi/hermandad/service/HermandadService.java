package com.nonomartinez.sfc.cofradiasapi.hermandad.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.controller.HermandadController;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<GetHermandadDTO> getFiveRandomHermandades() {
        List<Hermandad> allHermandades = hermandadRepository.findAll();

        // Si hay menos de 5 Hermandades, devolver todas las disponibles
        if (allHermandades.size() <= 5) {
            return allHermandades
                    .stream()
                    .map(GetHermandadDTO::of)
                    .toList();
        }

        Random random = new Random();
        List<Hermandad> randomHermandades = new ArrayList<>();
        List<Integer> selectedIndexes = new ArrayList<>();

        while (randomHermandades.size() < 5) {
            int randomIndex = random.nextInt(allHermandades.size());
            if (!selectedIndexes.contains(randomIndex)) {
                randomHermandades.add(allHermandades.get(randomIndex));
                selectedIndexes.add(randomIndex);
            }
        }

        return randomHermandades
                .stream()
                .map(GetHermandadDTO::of)
                .toList();
    }
}
