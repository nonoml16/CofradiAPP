package com.nonomartinez.sfc.cofradiasapi.musica.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.views.MusicaViews;

import java.util.UUID;

public record GetMusicaHermandadDTO(
        @JsonView({HermandadViews.HermandadDetails.class})
        UUID id,
        @JsonView({HermandadViews.HermandadDetails.class})
        String nombre
) {
    public static GetMusicaHermandadDTO of(Musica m){
        return new GetMusicaHermandadDTO(
                m.getId(),
                m.getNombre()
        );
    }
}
