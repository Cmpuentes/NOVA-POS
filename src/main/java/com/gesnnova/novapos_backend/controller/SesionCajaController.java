package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.domain.Usuario;
import com.gesnnova.novapos_backend.dto.CierreCajaRequest;
import com.gesnnova.novapos_backend.dto.SesionCajaRequest;
import com.gesnnova.novapos_backend.dto.SesionCajaResponse;
import com.gesnnova.novapos_backend.service.SesionCajaService;
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
@RequestMapping("/api/sesiones-caja")
@Tag(name = "Sesiones de Caja", description = "Gestión de apertura y cierre de caja")
public class SesionCajaController {

    @Autowired
    private SesionCajaService sesionCajaService;

    @Operation(summary = "Abrir sesión de caja",
            description = "Abre una nueva sesión de caja con el monto inicial de efectivo. El usuario que abre queda registrado automáticamente desde el token JWT.")
    @PostMapping("/abrir")
    @PreAuthorize("hasAuthority('CAJA_ABRIR')")
    public ResponseEntity<SesionCajaResponse> abrir(
            @Valid @RequestBody SesionCajaRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sesionCajaService.abrir(request, usuario.getId()));
    }

    @Operation(summary = "Cerrar sesión de caja",
            description = "Cierra una sesión de caja con el monto final contado. Calcula automáticamente la diferencia.")
    @PutMapping("/{id}/cerrar")
    @PreAuthorize("hasAuthority('CAJA_CERRAR')")
    public ResponseEntity<SesionCajaResponse> cerrar(
            @PathVariable UUID id,
            @Valid @RequestBody CierreCajaRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.ok(sesionCajaService.cerrar(id, request, usuario.getId()));
    }

    @Operation(summary = "Consultar sesión activa de una caja",
            description = "Devuelve la sesión actualmente abierta de una caja específica.")
    @GetMapping("/activa/{cajaId}")
    @PreAuthorize("hasAuthority('CAJA_ABRIR') or hasAuthority('CAJA_CERRAR')")
    public ResponseEntity<SesionCajaResponse> buscarSesionActiva(
            @PathVariable UUID cajaId) {
        return ResponseEntity.ok(sesionCajaService.buscarSesionActiva(cajaId));
    }

    @Operation(summary = "Listar sesiones por usuario",
            description = "Devuelve el historial de sesiones de caja de un usuario. Solo ADMINISTRADOR.")
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAuthority('CAJA_VER_HISTORIAL')")
    public ResponseEntity<List<SesionCajaResponse>> listarPorUsuario(
            @PathVariable UUID usuarioId) {
        return ResponseEntity.ok(sesionCajaService.listarPorUsuario(usuarioId));
    }
}
