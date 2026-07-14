package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.ImpuestoRequest;
import com.gesnnova.novapos_backend.dto.ImpuestoResponse;
import com.gesnnova.novapos_backend.service.ImpuestoService;
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
@RequestMapping("/api/impuestos")
@Tag(name = "Impuestos", description = "Gestión de tipos de impuesto para facturación electrónica DIAN")
public class ImpuestoController {

    @Autowired
    private ImpuestoService impuestoService;

    @Operation(summary = "Listar impuestos activos",
            description = "Devuelve todos los tipos de impuesto activos. Requiere token JWT.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<List<ImpuestoResponse>> listar() {
        return ResponseEntity.ok(impuestoService.listarTodos());
    }

    @Operation(summary = "Buscar impuesto por ID",
            description = "Devuelve un impuesto específico por su UUID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<ImpuestoResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(impuestoService.buscarPorId(id));
    }

    @Operation(summary = "Crear impuesto",
            description = "Crea un nuevo tipo de impuesto. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ImpuestoResponse> crear(
            @Valid @RequestBody ImpuestoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(impuestoService.crear(request));
    }

    @Operation(summary = "Actualizar impuesto",
            description = "Actualiza un tipo de impuesto existente. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<ImpuestoResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ImpuestoRequest request) {
        return ResponseEntity.ok(impuestoService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar impuesto",
            description = "Desactiva un tipo de impuesto sin borrarlo. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        impuestoService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
