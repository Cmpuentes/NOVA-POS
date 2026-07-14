package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, UUID> {

    List<UnidadMedida> findAll();

    boolean existsByAbreviatura(String abreviatura);
}
