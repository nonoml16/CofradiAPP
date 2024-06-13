package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadWebListDTO;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.views.UserViews;

import java.util.List;
import java.util.UUID;

public record GetPerfilWebDTO(
        @JsonView({UserViews.UserWebDetails.class})
        String username,
        @JsonView({UserViews.UserWebDetails.class})
        String email,
        @JsonView({UserViews.UserWebDetails.class})
        String nombre,
        @JsonView({UserViews.UserWebDetails.class})
        String apellidos,
        @JsonView({UserViews.UserWebDetails.class})
        String avatar,
        @JsonView({UserViews.UserWebDetails.class})
        UUID hermandadId,
        @JsonView({UserViews.UserWebDetails.class})
        List<GetHermandadDTO> hermandadesFavoritas,
        @JsonView({UserViews.UserWebDetails.class})
        List<GetCardDTO> cards
) {
    public static GetPerfilWebDTO of(User user){
        return new GetPerfilWebDTO(
                user.getUsername(),
                user.getEmail(),
                user.getNombre(),
                user.getApellidos(),
                user.getAvatar(),
                user.getHermandad().getId(),
                user.getHermandadesFavoritas().stream().map(GetHermandadDTO::of).toList(),
                user.getCards().stream().map(GetCardDTO::of).toList()
        );
    }
}
