package com.nonomartinez.sfc.cofradiasapi.paso.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PutPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.service.PasoService;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pasos")
public class PasoController {

    private final PasoService pasoService;

    @GetMapping("/paso/{id}")
    @JsonView(PasoViews.PasoDetailsView.class)
    public GetPasoDTO getPasoId(@PathVariable UUID id){

        return pasoService.getPaso(id);
    }

    @PostMapping("/paso/nuevo/{idHermandad}")
    public ResponseEntity<PostPasoDTO> addPasoHermandad(@PathVariable UUID idHermandad, @RequestBody PostPasoDTO postPasoDTO){
        return ResponseEntity.status(201).body(pasoService.addPaso(postPasoDTO, idHermandad));
    }

    @PutMapping("/paso/editar/{id}")
    public ResponseEntity<GetPasoDTO> editHermandad(@PathVariable UUID id, @RequestBody PutPasoDTO putPasoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(pasoService.edit(putPasoDTO, id));
    }

    @DeleteMapping("/paso/{id}")
    public ResponseEntity<?> deletePaso(@PathVariable UUID id){

        boolean borrado = pasoService.deletePaso(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/paso/{idPaso}/musica/{idMusica}")
    public ResponseEntity<?> deleteMusicaPaso(@PathVariable UUID idPaso, @PathVariable UUID idMusica){

        boolean borrado = pasoService.deleteMusicaPaso(idPaso, idMusica);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/paso/{idPaso}/musica/{idMusica}")
    public ResponseEntity<?> addMusicaPaso(@PathVariable UUID idPaso, @PathVariable UUID idMusica){

        boolean agregado = pasoService.addMusicaPaso(idPaso, idMusica);
        if(agregado)
            return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
