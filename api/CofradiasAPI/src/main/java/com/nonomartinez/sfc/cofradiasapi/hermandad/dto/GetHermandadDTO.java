package com.nonomartinez.sfc.cofradiasapi.hermandad.dto;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;

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
        List<Card> cards
) {
    public static GetHermandadDTO of(Hermandad h){
        return new GetHermandadDTO(
                h.getNombre(),
                h.getNombreCompleto(),
                h.getAnnoFundacion(),
                h.getDeInteres(),
                h.getNumNazarenos(),
                h.getNumHermanos(),
                h.getTiempoDePaso()
        )
    }
}
