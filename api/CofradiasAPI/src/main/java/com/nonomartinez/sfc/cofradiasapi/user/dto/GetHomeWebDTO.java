package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaListDTO;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;

import java.util.List;

public record GetHomeWebDTO(
        @JsonView({HomeViews.HomePageView.class})
        List<String> imagenes,
        @JsonView({HomeViews.HomePageView.class})
        int numHdades,
        @JsonView({HomeViews.HomePageView.class})
        int numBandas,
        @JsonView({HomeViews.HomePageView.class})
        int numCards,
        @JsonView({HomeViews.HomePageView.class})
        int numUsers,
        @JsonView({HomeViews.HomePageView.class})
        GetHermandadDTO hermandadDia,
        @JsonView({HomeViews.HomePageView.class})
        List<GetCardDTO> cardsDestacadas,
        @JsonView({HomeViews.HomePageView.class})
        List<GetHermandadDTO> hermandadesDestacadas,
        @JsonView({HomeViews.HomePageView.class})
        List<GetMusicaHermandadDTO> musicasDestacadas
) {
    public static GetHomeWebDTO of(
            List<String> imagenes,
            int numHdades,
            int numBandas,
            int numCards,
            int numUsers,
            GetHermandadDTO hermandadDia,
            List<GetCardDTO> cardsDestacadas,
            List<GetHermandadDTO> hermandadesDestacadas,
            List<GetMusicaHermandadDTO> musicasDestacadas
    ){
        return new GetHomeWebDTO(
                imagenes,
                numHdades,
                numBandas,
                numCards,
                numUsers,
                hermandadDia,
                cardsDestacadas,
                hermandadesDestacadas,
                musicasDestacadas
        );
    }
}
