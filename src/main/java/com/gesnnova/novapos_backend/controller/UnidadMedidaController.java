package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.UnidadMedidaRequest;
import com.gesnnova.novapos_backend.dto.UnidadMedidaResponse;
import com.gesnnova.novapos_backend.service.UnidadMedidaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/unidades-medida")
public class UnidadMedidaController {

    @Autowired
    private UnidadMedidaService unidadMedidaService;

    @Operation(summary = "Listar unidades de medida",
            description = "Devuelve todas las unidades de medida disponibles. Requiere token JWT.")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<List<UnidadMedidaResponse>> listar() {
        return ResponseEntity.ok(unidadMedidaService.listarTodas());
    }

    @Operation(summary = "Buscar unidad de medida por ID",
            description = "Devuelve una unidad de medida específica por su UUID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<UnidadMedidaResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(unidadMedidaService.buscarPorId(id));
    }

    @Operation(summary = "Crear unidad de medida",
            description = "Crea una nueva unidad de medida. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UnidadMedidaResponse> crear(
            @Valid @RequestBody UnidadMedidaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unidadMedidaService.crear(request));
    }

    @Operation(summary = "Actualizar unidad de medida",
            description = "Actualiza una unidad de medida existente. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<UnidadMedidaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UnidadMedidaRequest request) {
        return ResponseEntity.ok(unidadMedidaService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar unidad de medida",
            description = "Elimina una unidad de medida. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        unidadMedidaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
