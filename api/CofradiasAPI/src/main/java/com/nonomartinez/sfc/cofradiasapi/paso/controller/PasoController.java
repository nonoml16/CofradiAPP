package com.nonomartinez.sfc.cofradiasapi.paso.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.paso.dto.GetPasoDTO;
import com.nonomartinez.sfc.cofradiasapi.paso.service.PasoService;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
