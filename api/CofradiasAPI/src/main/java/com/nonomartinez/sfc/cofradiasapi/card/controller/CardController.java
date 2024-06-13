package com.nonomartinez.sfc.cofradiasapi.card.controller;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDetailsDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardWebDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.TipoCard;
import com.nonomartinez.sfc.cofradiasapi.card.service.CardService;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        return cardService.getAllPageable(pageable);
    }

    @GetMapping("/web/")
    public List<GetCardWebDTO> getAllWeb(){
        return cardService.getAll();
    }

    @GetMapping("/web/{tipoCard}")
    public List<GetCardWebDTO> getByTipo(@PathVariable TipoCard tipoCard){
        return cardService.getCardByTipo(tipoCard);
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<GetCardDetailsDTO> getCardById(@PathVariable Long id){
        return ResponseEntity.status(200).body(cardService.getCardByID(id));
    }

    @PostMapping("/nueva/{id}")
    public ResponseEntity<PostCardDTO> saveCard(@PathVariable UUID id, @RequestBody PostCardDTO postCardDTO){
        return ResponseEntity.status(201).body(cardService.addCard(postCardDTO, id));
    }

    @PutMapping("/card/{id}")
    public ResponseEntity<GetCardDTO> editCard(@PathVariable Long id, @RequestBody PostCardDTO postCardDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.edit(postCardDTO, id));
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id){

        boolean borrado = cardService.deleteCard(id);
        if(borrado)
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
