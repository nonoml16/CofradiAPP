package com.nonomartinez.sfc.cofradiasapi.paso.service;

import com.nonomartinez.sfc.cofradiasapi.paso.repository.PasoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasoService {

    private final PasoRepository pasoRepository;
}
