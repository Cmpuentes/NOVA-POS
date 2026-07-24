package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemVentaRepository extends JpaRepository<ItemVenta, UUID> {

    List<ItemVenta> findByVentaId(UUID ventaId);
}
