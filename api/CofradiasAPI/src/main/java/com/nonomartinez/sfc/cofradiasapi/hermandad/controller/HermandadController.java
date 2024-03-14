package com.nonomartinez.sfc.cofradiasapi.hermandad.controller;

import com.nonomartinez.sfc.cofradiasapi.hermandad.service.HermandadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HermandadController {

    private final HermandadService hermandadService;
}
