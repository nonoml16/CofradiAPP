package com.nonomartinez.sfc.cofradiasapi.card.controller;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.service.CardService;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user")
    public MyPage<GetCardDTO> getCardsUserAuth (@AuthenticationPrincipal User user, Pageable pageable){
        return cardService.getCardsUser(user.getId(), pageable);
    }

    @GetMapping("/user/{id}")
    public MyPage<GetCardDTO> getCardsUser (@PathVariable UUID id, Pageable pageable){
        return cardService.getCardsUser(id, pageable);
    }

    @GetMapping("/")
    public MyPage<GetCardDTO> getAll(Pageable pageable){
        return cardService.getAll(pageable);
    }

    @PostMapping("/nueva/{id}")
    public ResponseEntity<PostCardDTO> saveCard(@PathVariable UUID id, @RequestBody PostCardDTO postCardDTO){
        return ResponseEntity.status(201).body(cardService.addCard(postCardDTO, id));
    }
}
