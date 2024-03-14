package com.nonomartinez.sfc.cofradiasapi.musica.controller;

import com.nonomartinez.sfc.cofradiasapi.musica.service.MusicaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MusicaController {

    private final MusicaService musicaService;

}
