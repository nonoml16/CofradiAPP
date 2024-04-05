package com.nonomartinez.sfc.cofradiasapi.hermandad.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.views.HermandadViews;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.paso.controller.PasoController;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;

import java.util.ArrayList;
import java.util.List;

public record GetHermandadDTO(
        @JsonView({HermandadViews.HermandadList.class})
        String nombre,
        @JsonView({HermandadViews.HermandadDetails.class})
        String nombreCompleto,
        @JsonView({HermandadViews.HermandadList.class})
        String escudo,
        @JsonView({HermandadViews.HermandadDetails.class})
        int annoFundacion,
        @JsonView({HermandadViews.HermandadDetails.class})
        String deInteres,
        @JsonView({HermandadViews.HermandadDetails.class})
        int numNazarenos,
        @JsonView({HermandadViews.HermandadDetails.class})
        int numHermanos,
        @JsonView({HermandadViews.HermandadDetails.class})
        int tiempoPaso,
        @JsonView({HermandadViews.HermandadDetails.class})
        List<String> nombreBanda,
        @JsonView({HermandadViews.HermandadDetails.class})
        String sede,
        @JsonView({HermandadViews.HermandadDetails.class})
        List<GetCardDTO> cards
) {
    public static GetHermandadDTO of(Hermandad h){
        List<String> nombreBandas = new ArrayList<>();
        for (Paso paso:
             h.getPasos()) {
            for (Musica musica:
                 paso.getAcompannamiento()) {
                nombreBandas.add(musica.getNombre());
            }
        }
        return new GetHermandadDTO(
                h.getNombre(),
                h.getNombreCompleto(),
                h.getEscudo(),
                h.getAnnoFundacion(),
                h.getDeInteres(),
                h.getNumNazarenos(),
                h.getNumHermanos(),
                h.getTiempoDePaso(),
                nombreBandas,
                h.getSede(),
                h.getCards()
                        .stream()
                        .map(GetCardDTO::of)
                        .toList()
        );
    }
}
