package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.CategoriaRequest;
import com.gesnnova.novapos_backend.dto.CategoriaResponse;
import com.gesnnova.novapos_backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Gestión del catálogo de categorías de productos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar categorías activas",
    description = "Devuelve todas las categorías activas. Requiere token JWT")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(categoriaService.listarTodas());
    }

    @Operation(summary = "Buscar categoría por ID",
            description = "Devuelve una categoría específica por su UUID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<CategoriaResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @Operation(summary = "Crear categoría",
            description = "Crea una nueva categoría. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CategoriaResponse> crear(
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crear(request));
    }

    @Operation(summary = "Actualizar categoría",
            description = "Actualiza nombre y descripción de una categoría. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<CategoriaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CategoriaRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar categoría",
            description = "Desactiva una categoría sin borrarla. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        categoriaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
