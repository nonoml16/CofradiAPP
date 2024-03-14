package com.nonomartinez.sfc.cofradiasapi.hermandad.repository;

import com.nonomartinez.sfc.cofradiasapi.hermandad.model.Hermandad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HermandadRepository extends JpaRepository<Hermandad, UUID> {
}
