package com.nonomartinez.sfc.cofradiasapi.paso.controller;

import com.nonomartinez.sfc.cofradiasapi.paso.service.PasoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasoController {

    private final PasoService pasoService;
}
