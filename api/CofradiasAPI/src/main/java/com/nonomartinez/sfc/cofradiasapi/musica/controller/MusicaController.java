package com.nonomartinez.sfc.cofradiasapi.musica.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.service.MusicaService;
import com.nonomartinez.sfc.cofradiasapi.musica.views.MusicaViews;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musica")
public class MusicaController {

    private final MusicaService musicaService;

    //todo corregir
    @GetMapping("/{id}")
    @JsonView(MusicaViews.MusicaDetails.class)
    public GetMusicaDTO getHermandadId(@PathVariable UUID id){

        return musicaService.getMusicaDetails(id);
    }
}
