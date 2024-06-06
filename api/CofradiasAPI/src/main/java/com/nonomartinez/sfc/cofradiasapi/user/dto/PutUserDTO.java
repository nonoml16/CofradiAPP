package com.nonomartinez.sfc.cofradiasapi.user.dto;

public record PutUserDTO(
        String nombre,
        String apellidos,
        String avatar,
        String email,
        String username
) {
}
