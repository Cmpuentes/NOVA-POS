package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

    List<Categoria> findByActivaTrue();

    boolean existsByNombre(String nombre);
}
