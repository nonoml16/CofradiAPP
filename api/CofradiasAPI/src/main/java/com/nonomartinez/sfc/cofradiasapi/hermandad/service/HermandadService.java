package com.nonomartinez.sfc.cofradiasapi.hermandad.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.card.repository.CardRepository;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PostHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.PutHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import com.nonomartinez.sfc.cofradiasapi.paso.repository.PasoRepository;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HermandadService {

    private final HermandadRepository hermandadRepository;
    private final UserRepository userRepository;

    public List<GetHermandadDTO> getHermandadPorDia(Dias dias){
        return hermandadRepository.findHermandadByDia(dias).stream().map(GetHermandadDTO::of).toList();
    }

    public List<GetHermandadWebListDTO> getHermandadPorDiaWeb(Dias dias){
        return hermandadRepository.findHermandadByDia(dias).stream().map(GetHermandadWebListDTO::of).toList();
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

    public MyPage<GetHermandadWebListDTO> getAllHermandadesPaginable(Pageable pageable){
        Page<Hermandad> hdadPage = hermandadRepository.findAll(pageable);

        return MyPage.of(hdadPage.map(GetHermandadWebListDTO::of));
    }

    public List<GetHermandadWebListDTO> getAllHermandades(){
        return hermandadRepository.findAll().stream().map(GetHermandadWebListDTO::of).toList();
    }

    public PostHermandadDTO addHermandad(PostHermandadDTO nuevo){
        Hermandad hermandad = Hermandad.builder()
                .nombre(nuevo.nombre())
                .nombreCompleto(nuevo.nombreCompleto())
                .deInteres(nuevo.deInteres())
                .diaSalida(Dias.valueOf(nuevo.dia()))
                .annoFundacion(nuevo.annoFundacion())
                .numHermanos(nuevo.numHermanos())
                .numNazarenos(nuevo.numNazarenos())
                .tiempoDePaso(nuevo.tiempoPaso())
                .sede(nuevo.sede())
                .build();

        hermandadRepository.save(hermandad);
        return nuevo;
    }

    public GetHermandadDTO edit(PutHermandadDTO putHermandadDTO, UUID id){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(id);
        if(hermandadOptional.isEmpty())
            throw new NotFoundException("No existe hermandad");

        Hermandad editada = hermandadOptional.get();

        editada.setNombre(putHermandadDTO.nombre());
        editada.setNombreCompleto(putHermandadDTO.nombreCompleto());
        editada.setDeInteres(putHermandadDTO.deInteres());
        editada.setDiaSalida(Dias.valueOf(putHermandadDTO.dia()));
        editada.setAnnoFundacion(putHermandadDTO.annoFundacion());
        editada.setNumHermanos(putHermandadDTO.numHermanos());
        editada.setNumNazarenos(putHermandadDTO.numNazarenos());
        editada.setTiempoDePaso(putHermandadDTO.tiempoPaso());
        editada.setSede(putHermandadDTO.sede());

        hermandadRepository.save(editada);
        return GetHermandadDTO.of(editada);
    }

    @Transactional
    public boolean deleteHermandad(UUID id){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(id);
        if(hermandadOptional.isEmpty())
            throw new NotFoundException("No existe hermandad");

        Hermandad aBorrar = hermandadOptional.get();

        List<User> usersH = userRepository.findByHermandad(aBorrar);
        List<User> users = userRepository.findAll();
        for (User user : usersH) {
            user.setHermandad(null);
            userRepository.save(user);
        }

        for (User user : users) {
            if (user.getHermandadesFavoritas().contains(aBorrar)) {
                user.getHermandadesFavoritas().remove(aBorrar);
                userRepository.save(user);
            }
        }

        for (Card card : aBorrar.getCards()) {
            for (User user : userRepository.findAll()) {
                if (user.getCards().contains(card)) {
                    user.getCards().remove(card);
                    userRepository.save(user);
                }
            }
        }

        for(Paso paso : aBorrar.getPasos()){
            paso.getAcompannamiento().clear();
        }

        hermandadRepository.delete(aBorrar);

        return true;
    }

    public Dias getDia(UUID id){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(id);
        if(hermandadOptional.isEmpty())
            throw new NotFoundException("No existe hermandad");

        Hermandad hermandad = hermandadOptional.get();
        return hermandad.getDiaSalida();
    }
}
