package com.nonomartinez.sfc.cofradiasapi.card.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;

public record GetCardDTO(

        @JsonView({HermandadViews.HermandadDetails.class})
        Long id,
        @JsonView({HermandadViews.HermandadDetails.class})

        String titulo,
        @JsonView({HermandadViews.HermandadDetails.class})

        String urlImage,
        @JsonView({HermandadViews.HermandadDetails.class})

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
