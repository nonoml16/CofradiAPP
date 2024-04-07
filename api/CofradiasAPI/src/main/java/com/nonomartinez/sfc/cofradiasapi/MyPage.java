package com.nonomartinez.sfc.cofradiasapi;

import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record MyPage<T>(

        List<T> result,

        Integer size,

        Long totalElements,

        Integer pageNumber,

        boolean first,
        boolean last

)  {
    public static <T> MyPage<T> of(Page<T> page){
        return MyPage.<T>builder()
                .result(page.getContent())
                .first(page.isFirst())
                .last(page.isLast())
                .pageNumber(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .build();
    }
}