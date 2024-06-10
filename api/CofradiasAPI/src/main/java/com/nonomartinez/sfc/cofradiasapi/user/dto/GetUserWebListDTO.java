package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.nonomartinez.sfc.cofradiasapi.user.model.User;

import java.util.UUID;

public record GetUserWebListDTO(
        UUID id,
        String fotoPerfil,
        String apellidosNombre,
        String username,
        String nombreHermandad,
        int numCards
) {
    public static GetUserWebListDTO of(User user){
        return new GetUserWebListDTO(
               user.getId(),
               user.getAvatar(),
               user.getApellidos() + ", "+ user.getNombre(),
               user.getUsername(),
               user.getHermandad().getNombre(),
               user.getCards().size()
        );
    }
}
