package com.nonomartinez.sfc.cofradiasapi.musica.dto;

import com.nonomartinez.sfc.cofradiasapi.hermandad.dto.GetHermandadDTO;

import java.util.List;

public record GetMusicaDTO(
        String nombre,
        String anno,
        String localidad,
        List<GetHermandadDTO> hermandades
) {
}
