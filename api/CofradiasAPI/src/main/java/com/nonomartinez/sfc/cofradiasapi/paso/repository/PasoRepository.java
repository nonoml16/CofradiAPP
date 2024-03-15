package com.nonomartinez.sfc.cofradiasapi.paso.repository;

import com.nonomartinez.sfc.cofradiasapi.paso.model.Paso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PasoRepository extends JpaRepository<Paso, UUID> {
}
