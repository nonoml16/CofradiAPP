package com.nonomartinez.sfc.cofradiasapi.hermandad.dto;

import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.paso.controller.PasoController;
import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;

import java.util.ArrayList;
import java.util.List;

public record GetHermandadDTO(
        String nombre,
        String nombreCompleto,
        int annoFundacion,
        String deInteres,
        int numNazarenos,
        int numHermanos,
        int tiempoPaso,
        List<String> nombreBanda,
        String sede,
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
