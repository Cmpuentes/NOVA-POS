package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.SesionCaja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SesionCajaRepository extends JpaRepository<SesionCaja, UUID> {

    Optional<SesionCaja> findByCajaIdAndEstado(UUID cajaId, String estado);

    List<SesionCaja> findByUsuarioAperturaId(UUID usuarioId);

    boolean existsByCajaIdAndEstado(UUID cajaId, String estado);
}
