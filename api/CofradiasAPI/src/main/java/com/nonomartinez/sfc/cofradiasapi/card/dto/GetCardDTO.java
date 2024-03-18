package com.nonomartinez.sfc.cofradiasapi.card.dto;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;

public record GetCardDTO(

        Long id,
        String titulo,
        String urlImage,
        String nombreHermandad
) {
    public static GetCardDTO of(Card c){
        return new GetCardDTO(
                c.getId(),
                c.getTitulo(),
                c.getUrlImagen(),
                c.getHermandad().getNombre()
        );
    }
}
