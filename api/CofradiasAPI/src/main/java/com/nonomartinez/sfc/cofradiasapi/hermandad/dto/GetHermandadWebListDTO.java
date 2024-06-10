package com.nonomartinez.sfc.cofradiasapi.hermandad.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;

import java.util.UUID;

public record GetHermandadWebListDTO(
        UUID id,
        String escudo,
        String nombre,
        String dia,
        int numPasos,
        int numNazarenos,
        int numHnos
) {
    public static GetHermandadWebListDTO of(Hermandad hermandad){
        return new GetHermandadWebListDTO(
                hermandad.getId(),
                hermandad.getEscudo(),
                hermandad.getNombre(),
                hermandad.getDiaSalida().toString(),
                hermandad.getPasos().size(),
                hermandad.getNumNazarenos(),
                hermandad.getNumHermanos()
        );
    }
}
