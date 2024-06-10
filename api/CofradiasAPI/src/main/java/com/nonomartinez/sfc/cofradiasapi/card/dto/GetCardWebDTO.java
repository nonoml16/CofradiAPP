package com.nonomartinez.sfc.cofradiasapi.card.dto;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;

public record GetCardWebDTO(
        Long id,
        String nombreCard,
        String nombreHermandad,
        String tipoCard
) {
    public static GetCardWebDTO of(Card card){
        return new GetCardWebDTO(
                card.getId(),
                card.getTitulo(),
                card.getHermandad().getNombre(),
                card.getTipoCard().toString()
        );
    }
}
