package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;

import java.util.List;

public record GetPerfilDTO(
        String imagenPerfil,
        String nombreApellidos,
        String nombreHermandad,
        List<GetHermandadDTO> hermandadesFavoritas,
        List<GetCardDTO> cards
) {
    public GetPerfilDTO of(User user){
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
