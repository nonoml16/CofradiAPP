package com.nonomartinez.sfc.cofradiasapi.musica.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;

import java.util.UUID;

public record GetMusicaHermandadDTO(
        @JsonView({
                HermandadViews.HermandadDetails.class,
                HomeViews.HomePageView.class,
                PasoViews.PasoDetailsView.class
        })
        UUID id,
        @JsonView({
                HermandadViews.HermandadDetails.class,
                HomeViews.HomePageView.class,
                PasoViews.PasoDetailsView.class
        })
        String nombre
) {
    public static GetMusicaHermandadDTO of(Musica m){
        return new GetMusicaHermandadDTO(
                m.getId(),
                m.getNombre()
        );
    }
}
