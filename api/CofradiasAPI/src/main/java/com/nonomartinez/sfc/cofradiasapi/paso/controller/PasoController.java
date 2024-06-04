package com.nonomartinez.sfc.cofradiasapi.paso.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.PostPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.service.PasoService;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;
import lombok.RequiredArgsConstructor;
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
}
