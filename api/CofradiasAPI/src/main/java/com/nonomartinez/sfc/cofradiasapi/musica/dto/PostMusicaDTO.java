package com.nonomartinez.sfc.cofradiasapi.musica.dto;

public record PostMusicaDTO(
        String nombre,
        String localidad,
        String tipo,
        int annoFundacion
) {
}
