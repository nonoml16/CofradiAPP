package com.nonomartinez.sfc.cofradiasapi.hermandad.repository;

import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Dias;
import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HermandadRepository extends JpaRepository<Hermandad, UUID> {

    @Query("SELECT h FROM Hermandad h WHERE h.diaSalida = ?1")
    List<Hermandad> findHermandadByDia(Dias dia);
}
