package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.views.UserViews;

import java.util.List;

public record GetPerfilDTO(
        @JsonView({UserViews.UserDetails.class})
        String imagenPerfil,
        @JsonView({UserViews.UserDetails.class})
        String nombreApellidos,
        @JsonView({UserViews.UserDetails.class})
        String nombreHermandad,
        @JsonView({UserViews.UserDetails.class})
        List<GetHermandadDTO> hermandadesFavoritas,
        @JsonView({UserViews.UserDetails.class})
        List<GetCardDTO> cards
) {
    public static GetPerfilDTO of(User user){
        return new GetPerfilDTO(
                user.getAvatar(),
                user.getNombre() + " " + user.getApellidos(),
                user.getHermandad().getNombre(),
                user.getHermandadesFavoritas()
                        .stream()
                        .map(GetHermandadDTO::of)
                        .toList(),
                user.getCards()
                        .stream()
                        .map(GetCardDTO::of)
                        .toList()
        );
    }
}
