package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {

    List<Producto> findByActivoTrue();

    List<Producto> findByCategoriaIdAndActivoTrue(UUID categoriaId);

    Optional<Producto> findByCodigoAndActivoTrue(String codigo);

    boolean existsByCodigo(String codigo);
}
