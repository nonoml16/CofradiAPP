package com.nonomartinez.sfc.cofradiasapi.card.service;

import com.nonomartinez.sfc.cofradiasapi.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
}
