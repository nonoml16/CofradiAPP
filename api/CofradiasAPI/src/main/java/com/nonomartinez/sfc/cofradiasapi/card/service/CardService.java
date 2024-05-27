package com.nonomartinez.sfc.cofradiasapi.card.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.card.repository.CardRepository;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    public MyPage<GetCardDTO> getCardsUser (UUID idUser, Pageable pageable){
        Optional<User>  optionalUser = userRepository.findById(idUser);
        if(optionalUser.isEmpty())
            throw new NotFoundException("");

        Page<Card> pageSelected = userRepository.findCardsByUserId(idUser, pageable);


        return MyPage.of(pageSelected.map(GetCardDTO::of));

    }

    public MyPage<GetCardDTO> getAll(Pageable pageable){
        Page<Card> cardPage = cardRepository.findAll(pageable);

        return MyPage.of(cardPage.map(GetCardDTO::of));
    }

    public List<GetCardDTO> getFiveRandomCards() {
        List<Card> allCards = cardRepository.findAll();

        // Si hay menos de 5 Cards, devolver todas las disponibles
        if (allCards.size() <= 5) {
            return allCards
                    .stream()
                    .map(GetCardDTO::of)
                    .toList();
        }

        Random random = new Random();
        List<Card> randomCards = new ArrayList<>();
        List<Integer> selectedIndexes = new ArrayList<>();

        while (randomCards.size() < 5) {
            int randomIndex = random.nextInt(allCards.size());
            if (!selectedIndexes.contains(randomIndex)) {
                randomCards.add(allCards.get(randomIndex));
                selectedIndexes.add(randomIndex);
            }
        }

        return randomCards
                .stream()
                .map(GetCardDTO::of)
                .toList();
    }

}