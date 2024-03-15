package com.nonomartinez.sfc.cofradiasapi.musica.service;

import com.nonomartinez.sfc.cofradiasapi.musica.repository.MusicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicaService {

    private final MusicaRepository musicaRepository;
}
