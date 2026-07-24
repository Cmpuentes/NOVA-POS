package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VentaRepository extends JpaRepository<Venta, UUID> {

    List<Venta> findBySesionCajaId(UUID sesionCajaId);

    List<Venta> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT COALESCE(SUM(p.monto), 0) FROM Pago p " +
            "WHERE p.venta.sesionCaja.id = :sesionId " +
            "AND p.metodo = 'EFECTIVO'")
    BigDecimal sumVentasEfectivoBySesion(@Param("sesionId") UUID sesionId);
}
