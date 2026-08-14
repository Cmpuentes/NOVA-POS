package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.CajaRequest;
import com.gesnnova.novapos_backend.dto.CajaResponse;
import com.gesnnova.novapos_backend.service.CajaService;
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
@RequestMapping("/api/cajas")
@Tag(name = "Cajas", description = "Gestión de terminales POS")
public class CajaController {

    @Autowired
    private CajaService cajaService;

    @Operation(summary = "Listar cajas activas",
            description = "Devuelve todas las cajas activas. Solo ADMINISTRADOR.")
    @GetMapping
    @PreAuthorize("hasAuthority('CAJAS_GESTIONAR')")
    public ResponseEntity<List<CajaResponse>> listar() {
        return ResponseEntity.ok(cajaService.listarTodas());
    }

    @Operation(summary = "Buscar caja por ID",
            description = "Devuelve una caja específica por su UUID. Solo ADMINISTRADOR.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAJAS_GESTIONAR')")
    public ResponseEntity<CajaResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(cajaService.buscarPorId(id));
    }

    @Operation(summary = "Crear caja",
            description = "Registra una nueva terminal POS. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('CAJAS_GESTIONAR')")
    public ResponseEntity<CajaResponse> crear(
            @Valid @RequestBody CajaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cajaService.crear(request));
    }

    @Operation(summary = "Actualizar caja",
            description = "Actualiza los datos de una caja. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CAJAS_GESTIONAR')")
    public ResponseEntity<CajaResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody CajaRequest request) {
        return ResponseEntity.ok(cajaService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar caja",
            description = "Desactiva una caja sin borrarla. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAJAS_GESTIONAR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        cajaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
