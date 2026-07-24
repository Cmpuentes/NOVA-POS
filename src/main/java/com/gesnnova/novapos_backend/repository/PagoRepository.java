package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PagoRepository extends JpaRepository<Pago, UUID> {

    List<Pago> findByVentaId(UUID ventaId);
}
