package com.nonomartinez.sfc.cofradiasapi.card.dto;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.card.model.TipoCard;

public record GetCardDetailsDTO(
        Long id,
        String urlImagen,
        String titulo,
        String descripcion,
        String nombreFotografo,
        TipoCard tipoCard
) {
    public static GetCardDetailsDTO of(Card card){
        return new GetCardDetailsDTO(
                card.getId(),
                card.getUrlImagen(),
                card.getTitulo(),
                card.getDescripcion(),
                card.getNombreFotografo(),
                card.getTipoCard()
        );
    }
}
