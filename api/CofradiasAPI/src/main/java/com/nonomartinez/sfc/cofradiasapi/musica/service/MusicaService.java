package com.nonomartinez.sfc.cofradiasapi.musica.service;

import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.repository.MusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;

    public GetMusicaDTO getMusicaDetails(UUID id){
        Optional<Musica> musicaOptional = musicaRepository.findById(id);
        if(musicaOptional.isEmpty())
            throw new NotFoundException("Musica");

        return GetMusicaDTO.of(musicaOptional.get());
    }
}
