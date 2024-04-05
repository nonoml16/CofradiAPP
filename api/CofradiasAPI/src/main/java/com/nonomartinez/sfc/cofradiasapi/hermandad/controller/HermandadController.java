package com.nonomartinez.sfc.cofradiasapi.hermandad.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.service.HermandadService;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hermandades")
public class HermandadController {

    private final HermandadService hermandadService;

    @GetMapping("/{dia}")
    @JsonView(HermandadViews.HermandadList.class)
    public List<GetHermandadDTO> getAllHermandadesFromDiaSalida(@PathVariable Dias dia){
        return hermandadService.getHermandadPorDia(dia);
    }
}
