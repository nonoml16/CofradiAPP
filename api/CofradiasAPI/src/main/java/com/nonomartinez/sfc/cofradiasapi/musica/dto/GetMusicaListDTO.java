package com.nonomartinez.sfc.cofradiasapi.musica.dto;

import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;

import java.util.UUID;

public record GetMusicaListDTO(
        UUID id,
        String nombre,
        String tipo,
        int numHermandades
) {
    public static GetMusicaListDTO of(Musica musica){
        return new GetMusicaListDTO(
                musica.getId(),
                musica.getNombre(),
                musica.getTipoBanda().toString(),
                musica.getPasos().size()
        );
    }
}
