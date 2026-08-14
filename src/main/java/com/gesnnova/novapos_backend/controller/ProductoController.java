package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.ProductoRequest;
import com.gesnnova.novapos_backend.dto.ProductoResponse;
import com.gesnnova.novapos_backend.service.ProductoService;
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
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión del catálogo de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar productos activos",
            description = "Devuelve todos los productos activos con sus datos de categoría, impuesto y unidad de medida.")
    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCTOS_VER')")
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @Operation(summary = "Listar productos por categoría",
            description = "Devuelve los productos activos de una categoría específica.")
    @GetMapping("/categoria/{categoriaId}")
    @PreAuthorize("hasAuthority('PRODUCTOS_VER')")
    public ResponseEntity<List<ProductoResponse>> listarPorCategoria(
            @PathVariable UUID categoriaId) {
        return ResponseEntity.ok(productoService.listarPorCategoria(categoriaId));
    }

    @Operation(summary = "Buscar producto por ID",
            description = "Devuelve un producto específico por su UUID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCTOS_VER')")
    public ResponseEntity<ProductoResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    @Operation(summary = "Buscar producto por código de barras",
            description = "Busca un producto por su código. Se invoca cuando el lector de barras escanea un producto en caja.")
    @GetMapping("/codigo/{codigo}")
    @PreAuthorize("hasAuthority('PRODUCTOS_VER')")
    public ResponseEntity<ProductoResponse> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(productoService.buscarPorCodigo(codigo));
    }

    @Operation(summary = "Crear producto",
            description = "Crea un nuevo producto en el catálogo. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCTOS_CREAR')")
    public ResponseEntity<ProductoResponse> crear(
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.crear(request));
    }

    @Operation(summary = "Actualizar producto",
            description = "Actualiza los datos de un producto existente. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCTOS_EDITAR')")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar producto",
            description = "Desactiva un producto sin borrarlo. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCTOS_DESACTIVAR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        productoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
