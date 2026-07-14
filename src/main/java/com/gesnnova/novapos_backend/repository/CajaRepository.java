package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Caja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CajaRepository extends JpaRepository<Caja, UUID> {

    List<Caja> findByActivaTrue();

    boolean existsByNombre(String nombre);
}
