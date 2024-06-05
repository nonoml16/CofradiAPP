package com.nonomartinez.sfc.cofradiasapi.card.dto;

public record PostCardDTO(
        String urlImagen,
        String titulo,
        String descripcion,
        String nombreFotografo,
        String tipo
) {
}
