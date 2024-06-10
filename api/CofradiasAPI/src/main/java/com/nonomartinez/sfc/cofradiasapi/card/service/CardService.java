package com.nonomartinez.sfc.cofradiasapi.card.service;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.GetCardWebDTO;
import com.nonomartinez.sfc.cofradiasapi.card.dto.PostCardDTO;
import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import com.nonomartinez.sfc.cofradiasapi.card.model.TipoCard;
import com.nonomartinez.sfc.cofradiasapi.card.repository.CardRepository;
import com.nonomartinez.sfc.cofradiasapi.exception.NotFoundException;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import com.nonomartinez.sfc.cofradiasapi.hermandad.repository.HermandadRepository;
import com.nonomartinez.sfc.cofradiasapi.user.model.User;
import com.nonomartinez.sfc.cofradiasapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final HermandadRepository hermandadRepository;

    public MyPage<GetCardDTO> getCardsUser (UUID idUser, Pageable pageable){
        Optional<User>  optionalUser = userRepository.findById(idUser);
        if(optionalUser.isEmpty())
            throw new NotFoundException("");

        Page<Card> pageSelected = userRepository.findCardsByUserId(idUser, pageable);


        return MyPage.of(pageSelected.map(GetCardDTO::of));

    }

    public MyPage<GetCardDTO> getAllPageable(Pageable pageable){
        Page<Card> cardPage = cardRepository.findAll(pageable);

        return MyPage.of(cardPage.map(GetCardDTO::of));
    }

    public List<GetCardWebDTO> getAll(){

        return cardRepository.findAll().stream().map(GetCardWebDTO::of).toList();
    }

    public List<GetCardWebDTO> getCardByTipo(TipoCard tipoCard){

        return cardRepository.findCardsByTipo(tipoCard).stream().map(GetCardWebDTO::of).toList();
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

    public PostCardDTO addCard(PostCardDTO postCardDTO, UUID idHermandad){
        Optional<Hermandad> hermandadOptional = hermandadRepository.findById(idHermandad);
        if(hermandadOptional.isEmpty())
            throw new NotFoundException("No existe hermandad");

        Hermandad hermandad = hermandadOptional.get();
        Card card = Card.builder()
                .urlImagen(postCardDTO.urlImagen())
                .titulo(postCardDTO.titulo())
                .descripcion(postCardDTO.descripcion())
                .nombreFotografo(postCardDTO.nombreFotografo())
                .tipoCard(TipoCard.valueOf(postCardDTO.tipo()))
                .hermandad(hermandad)
                .build();

        cardRepository.save(card);

        return postCardDTO;
    }

    public GetCardDTO edit(PostCardDTO postCardDTO, Long id){
        Optional<Card> cardOptional = cardRepository.findById(id);
        if(cardOptional.isEmpty())
            throw new NotFoundException("No existe la card");

        Card editada = cardOptional.get();

        editada.setUrlImagen(postCardDTO.urlImagen());
        editada.setTitulo(postCardDTO.titulo());
        editada.setDescripcion(postCardDTO.descripcion());
        editada.setNombreFotografo(postCardDTO.nombreFotografo());
        editada.setTipoCard(TipoCard.valueOf(postCardDTO.tipo()));

        cardRepository.save(editada);
        return GetCardDTO.of(editada);

    }

    @Transactional
    public boolean deleteCard(Long id){
        Optional<Card> cardOptional = cardRepository.findById(id);
        if(cardOptional.isEmpty())
            throw new NotFoundException("No existe la card");

        Card aBorrar = cardOptional.get();
        
        Hermandad hermandad = aBorrar.getHermandad();
        hermandad.getCards().remove(aBorrar);
        hermandadRepository.save(hermandad);

        List<User> users = userRepository.findAll();
        for(User user : users){
            if(user.getCards().contains(aBorrar)){
                user.getCards().remove(aBorrar);
                userRepository.save(user);
            }
        }

        cardRepository.delete(aBorrar);

        return true;
    }

}