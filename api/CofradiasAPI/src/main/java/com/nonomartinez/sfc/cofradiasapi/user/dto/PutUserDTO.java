package com.nonomartinez.sfc.cofradiasapi.user.dto;

import java.util.UUID;

public record PutUserDTO(
        String nombre,
        String apellidos,
        String avatar,
        String email,
        String username,
        UUID idHermandad
) {
}
