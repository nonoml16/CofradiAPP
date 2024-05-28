package com.nonomartinez.sfc.cofradiasapi.paso.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import com.nonomartinez.sfc.cofradiasapi.paso.views.PasoViews;

import java.util.List;
import java.util.UUID;

public record GetPasoDTO(
        @JsonView({PasoViews.PasoDetailsView.class, HermandadViews.HermandadDetails.class})
        UUID id,
        @JsonView({PasoViews.PasoDetailsView.class, HermandadViews.HermandadDetails.class})
        String imagen,
        @JsonView({PasoViews.PasoDetailsView.class})
        String capataz,
        @JsonView({PasoViews.PasoDetailsView.class})
        int numCostaleros,
        @JsonView({PasoViews.PasoDetailsView.class})
        List<GetMusicaHermandadDTO> acompannamiento,
        @JsonView({PasoViews.PasoDetailsView.class})
        String hermandad
) {
    public static GetPasoDTO of(Paso p){
        return new GetPasoDTO(
                p.getId(),
                p.getImagen(),
                p.getCapataz(),
                p.getNumCostaleros(),
                p.getAcompannamiento()
                        .stream()
                        .map(GetMusicaHermandadDTO::of)
                        .toList(),
                p.getHermandad().getNombre()
        );

    }
}
