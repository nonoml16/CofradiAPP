package com.nonomartinez.sfc.cofradiasapi.user.dto;

import java.util.UUID;

public record PostUserDTO(
        String username,
        String password,
        String email,
        String nombre,
        String apellidos,
        String avatar,
        UUID hermandadId
) {
}
