package com.nonomartinez.sfc.cofradiasapi.card.repository;

import com.nonomartinez.sfc.cofradiasapi.card.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    //Page<Card> findAll(Pageable pageable);
}
