package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.domain.Usuario;
import com.gesnnova.novapos_backend.dto.VentaRequest;
import com.gesnnova.novapos_backend.dto.VentaResponse;
import com.gesnnova.novapos_backend.service.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Registro y consulta de ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Registrar venta",
            description = "Registra una venta completa con sus ítems y pagos. Disponible para CAJERO y ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<VentaResponse> registrar(
            @Valid @RequestBody VentaRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrar(request, usuario.getId()));
    }

    @Operation(summary = "Listar ventas por sesión de caja",
            description = "Devuelve todas las ventas de una sesión de caja específica.")
    @GetMapping("/sesion/{sesionId}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<List<VentaResponse>> listarPorSesion(
            @PathVariable UUID sesionId) {
        return ResponseEntity.ok(ventaService.listarPorSesion(sesionId));
    }

    @Operation(summary = "Buscar venta por ID",
            description = "Devuelve el detalle completo de una venta con sus ítems y pagos.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRADOR', 'CAJERO')")
    public ResponseEntity<VentaResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }
}
