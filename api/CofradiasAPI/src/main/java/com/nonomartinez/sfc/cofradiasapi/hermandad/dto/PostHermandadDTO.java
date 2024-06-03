package com.nonomartinez.sfc.cofradiasapi.hermandad.dto;

public record PostHermandadDTO(
        String nombre,
        String nombreCompleto,
        String deInteres,
        String dia,
        int annoFundacion,
        int numHermanos,
        int numNazarenos,
        int tiempoPaso,
        String sede
) {
}
