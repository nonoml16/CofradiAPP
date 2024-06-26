package com.nonomartinez.sfc.cofradiasapi.card.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;
import com.nonomartinez.sfc.cofradiasapi.user.views.UserViews;

public record GetCardDTO(

        @JsonView({
                HermandadViews.HermandadDetails.class,
                UserViews.UserDetails.class,
                HomeViews.HomePageView.class,
                UserViews.UserWebDetails.class
        })
        Long id,
        @JsonView({
                HermandadViews.HermandadDetails.class,
                UserViews.UserDetails.class,
                HomeViews.HomePageView.class,
                UserViews.UserWebDetails.class
        })
        String titulo,
        @JsonView({
                HermandadViews.HermandadDetails.class,
                UserViews.UserDetails.class,
                HomeViews.HomePageView.class,
                UserViews.UserWebDetails.class
        })
        String imagen,
        @JsonView({
                HermandadViews.HermandadDetails.class,
                UserViews.UserDetails.class,
                HomeViews.HomePageView.class,
                UserViews.UserWebDetails.class
        })
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
