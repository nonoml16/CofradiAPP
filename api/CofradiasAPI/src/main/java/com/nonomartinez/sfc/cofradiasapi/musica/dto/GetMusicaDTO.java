package com.nonomartinez.sfc.cofradiasapi.musica.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.views.MusicaViews;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record GetMusicaDTO(
        @JsonView({MusicaViews.MusicaDetails.class})
        UUID id,
        @JsonView({MusicaViews.MusicaDetails.class})
        String nombre,
        @JsonView({MusicaViews.MusicaDetails.class})
        int anno,
        @JsonView({MusicaViews.MusicaDetails.class})
        String localidad,
        @JsonView({MusicaViews.MusicaDetails.class})
        List<GetHermandadDTO> hermandades
) {
    public static GetMusicaDTO of(Musica m){
        List<Hermandad> hermandades = new ArrayList<>();
        for (Paso paso:
                m.getPasos()) {
            hermandades.add(paso.getHermandad());

        }
        return new GetMusicaDTO(
                m.getId(),
                m.getNombre(),
                m.getAnnoFundacion(),
                m.getLocalidad(),
                hermandades.stream().map(GetHermandadDTO::of).toList()
        );
    }
}
