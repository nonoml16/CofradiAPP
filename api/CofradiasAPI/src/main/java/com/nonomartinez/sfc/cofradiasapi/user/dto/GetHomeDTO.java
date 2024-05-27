package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.musica.dto.GetMusicaHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.user.views.HomeViews;

import java.util.List;

public record GetHomeDTO(
        @JsonView({HomeViews.HomePageView.class})
        List<String> carruselFotos,
        @JsonView({HomeViews.HomePageView.class})
        GetHermandadDTO hermandadDelDia,
        @JsonView({HomeViews.HomePageView.class})
        List<GetCardDTO> cardsDestacadas,
        @JsonView({HomeViews.HomePageView.class})
        List<GetHermandadDTO> hermandadesDestacadas,
        @JsonView({HomeViews.HomePageView.class})
        List<GetMusicaHermandadDTO> bandasDestacadas
) {
    public static GetHomeDTO of(
            List<String> fotos,
            GetHermandadDTO hDia,
            List<GetCardDTO> cardsD,
            List<GetHermandadDTO> hdadesD,
            List<GetMusicaHermandadDTO> musicD
    ){
        return new GetHomeDTO(
                fotos,
                hDia,
                cardsD,
                hdadesD,
                musicD
        );
    }
}
