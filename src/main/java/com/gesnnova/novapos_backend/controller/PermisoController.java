package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.AsignarPermisosRequest;
import com.gesnnova.novapos_backend.dto.PermisoResponse;
import com.gesnnova.novapos_backend.service.PermisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permisos")
@Tag(name = "Permisos", description = "Gestión de permisos y asignación a roles")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @Operation(summary = "Listar todos los permisos",
            description = "Devuelve todos los permisos disponibles en el sistema. Solo ROLES_GESTIONAR.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<List<PermisoResponse>> listar() {
        return ResponseEntity.ok(permisoService.listarTodos());
    }

    @Operation(summary = "Ver permisos de un rol",
            description = "Devuelve todos los permisos asignados a un rol específico.")
    @GetMapping("/rol/{rolId}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<List<PermisoResponse>> listarPorRol(@PathVariable UUID rolId) {
        return ResponseEntity.ok(permisoService.listarPorRol(rolId));
    }

    @Operation(summary = "Asignar permisos a un rol",
            description = "Reemplaza todos los permisos de un rol con los nuevos enviados. Solo ROLES_GESTIONAR.")
    @PostMapping("/rol/{rolId}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<Void> asignarPermisos(
            @PathVariable UUID rolId,
            @Valid @RequestBody AsignarPermisosRequest request) {
        permisoService.asignarPermisos(rolId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Quitar un permiso a un rol",
            description = "Elimina un permiso específico de un rol. Solo ROLES_GESTIONAR.")
    @DeleteMapping("/rol/{rolId}/permiso/{permisoId}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<Void> quitarPermiso(
            @PathVariable UUID rolId,
            @PathVariable UUID permisoId) {
        permisoService.quitarPermiso(rolId, permisoId);
        return ResponseEntity.noContent().build();
    }
}
