package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto, UUID> {

    List<Impuesto> findByActivoTrue();

    boolean existsByNombre(String nombre);

    boolean existsByCodigoDian(String codigoDian);
}
