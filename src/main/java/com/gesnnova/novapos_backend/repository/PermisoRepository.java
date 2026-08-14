package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, UUID> {

    List<Permiso> findAll();

    Optional<Permiso> findByCodigo(String codigo);
}
