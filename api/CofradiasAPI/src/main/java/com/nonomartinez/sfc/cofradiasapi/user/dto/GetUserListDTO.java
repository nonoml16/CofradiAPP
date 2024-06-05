package com.nonomartinez.sfc.cofradiasapi.user.dto;

import com.nonomartinez.sfc.cofradiasapi.user.model.User;

public record GetUserListDTO(
        String imagenPerfil,
        String apellidosNombre,
        String username,
        String nombreHdad
) {
    public static GetUserListDTO of(User user){
        return new GetUserListDTO(
                user.getAvatar(),
                user.getApellidos() + ", " + user.getNombre(),
                user.getUsername(),
                user.getHermandad().getNombre()
        );
    }
}
