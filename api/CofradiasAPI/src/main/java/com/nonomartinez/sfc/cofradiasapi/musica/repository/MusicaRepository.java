package com.nonomartinez.sfc.cofradiasapi.musica.repository;

import com.nonomartinez.sfc.cofradiasapi.MyPage;
import com.nonomartinez.sfc.cofradiasapi.musica.model.Musica;
import com.nonomartinez.sfc.cofradiasapi.musica.model.TipoBanda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, UUID> {

    @Query("SELECT m FROM Musica m WHERE m.tipoBanda = ?1")
    Page<Musica> findBandasByTipo(TipoBanda tipoBanda, Pageable pageable);

    @Query("SELECT m FROM Musica m WHERE m.tipoBanda = ?1")
    List<Musica> findBandasByTipo(TipoBanda tipoBanda);
}
