package com.nonomartinez.sfc.cofradiasapi.musica.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.PostMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.TipoBanda;
import com.nonomartinez.sfc.cofradiasapi.musica.service.MusicaService;
import com.nonomartinez.sfc.cofradiasapi.musica.views.MusicaViews;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/musica")
public class MusicaController {

    private final MusicaService musicaService;

    //todo corregir
    @GetMapping("/{id}")
    @JsonView(MusicaViews.MusicaDetails.class)
    public GetMusicaDTO getMusicaDetails(@PathVariable UUID id){

        return musicaService.getMusicaDetails(id);
    }

    @GetMapping("/")
    public MyPage<GetMusicaListDTO> getAllBandas(Pageable pageable){
        return musicaService.getAllBandas(pageable);
    }

    @GetMapping("/tipo/{tipoBanda}")
    public MyPage<GetMusicaListDTO> getBandasTipo(@PathVariable TipoBanda tipoBanda, Pageable pageable){
        return musicaService.getBandasPorTipo(tipoBanda, pageable);
    }

    @PostMapping("/nueva")
    public ResponseEntity<PostMusicaDTO> saveMusica(@RequestBody PostMusicaDTO postMusicaDTO){
        return ResponseEntity.status(201).body(musicaService.addMusica(postMusicaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMusicaDTO> editMusica(@PathVariable UUID id, @RequestBody PostMusicaDTO postMusicaDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(musicaService.edit(postMusicaDTO, id));
    }
}
