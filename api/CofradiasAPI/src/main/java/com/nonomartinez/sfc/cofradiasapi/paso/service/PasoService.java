package com.nonomartinez.sfc.cofradiasapi.paso.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.repository.MusicaRepository;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PutPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import com.nonomartinez.sfc.cofradiasapi.paso.repository.PasoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasoService {

    private final PasoRepository pasoRepository;
    private final HermandadRepository hermandadRepository;
    private final MusicaRepository musicaRepository;

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

    public GetPasoDTO edit(PutPasoDTO putPasoDTO, UUID id){
        Optional<Paso> optionalPaso = pasoRepository.findById(id);
        if(optionalPaso.isEmpty())
            throw new NotFoundException("No existe el paso");

        Paso aEditar = optionalPaso.get();
        aEditar.setCapataz(putPasoDTO.capataz());
        aEditar.setNumCostaleros(putPasoDTO.numCostaleros());

        pasoRepository.save(aEditar);

        return GetPasoDTO.of(aEditar);
    }

    @Transactional
    public boolean deletePaso(UUID id){
        Optional<Paso> optionalPaso = pasoRepository.findById(id);
        if(optionalPaso.isEmpty())
            throw new NotFoundException("No existe el paso");

        Paso borrar = optionalPaso.get();

        borrar.getAcompannamiento().clear();
        borrar.setHermandad(null);

        pasoRepository.delete(borrar);
        return true;
    }

    @Transactional
    public boolean deleteMusicaPaso(UUID idPaso, UUID idMusica){
        Optional<Paso> optionalPaso = pasoRepository.findById(idPaso);
        Optional<Musica> musicaOptional = musicaRepository.findById(idMusica);

        if(optionalPaso.isEmpty())
            throw new NotFoundException("No existe el paso");
        if(musicaOptional.isEmpty())
            throw new NotFoundException("No existe la banda");

        Paso borrar = optionalPaso.get();
        Musica eliminar = musicaOptional.get();

        borrar.getAcompannamiento().remove(eliminar);
        pasoRepository.save(borrar);

        return true;

    }

    public boolean addMusicaPaso(UUID idPaso, UUID idMusica){
        Optional<Paso> optionalPaso = pasoRepository.findById(idPaso);
        Optional<Musica> musicaOptional = musicaRepository.findById(idMusica);

        if(optionalPaso.isEmpty())
            throw new NotFoundException("No existe el paso");
        if(musicaOptional.isEmpty())
            throw new NotFoundException("No existe la banda");

        Paso editar = optionalPaso.get();
        Musica agregar = musicaOptional.get();

        editar.getAcompannamiento().add(agregar);
        pasoRepository.save(editar);

        return true;

    }
}
