package com.nonomartinez.sfc.cofradiasapi.hermandad.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public GetHermandadDTO getHermandadDia(){
        List<Hermandad> allHermandades = hermandadRepository.findAll();

        if (allHermandades.isEmpty()) {
            throw new NotFoundException("No hay hermandades disponibles.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(allHermandades.size());

        Hermandad randomHermandad = allHermandades.get(randomIndex);

        return GetHermandadDTO.of(randomHermandad);
    }

    public List<String> getFiveRandomFotos() {
        List<Hermandad> allHermandades = hermandadRepository.findAll();

        Random random = new Random();
        List<String> fotosRandom = new ArrayList<>();
        List<Integer> selectedIndexes = new ArrayList<>();

        while (fotosRandom.size() < 5) {
            int randomIndex = random.nextInt(allHermandades.size());
            if (!selectedIndexes.contains(randomIndex)) {
                List<String> galeria = allHermandades.get(randomIndex).getGaleriaImagenes();
                if(galeria.isEmpty())
                    fotosRandom.add(null);
                else fotosRandom.add(galeria.get(0));
                selectedIndexes.add(randomIndex);
            }
        }

        return fotosRandom;
    }

    public MyPage<GetHermandadWebListDTO> getAllHermandades(Pageable pageable){
        Page<Hermandad> hdadPage = hermandadRepository.findAll(pageable);

        return MyPage.of(hdadPage.map(GetHermandadWebListDTO::of));
    }
}
