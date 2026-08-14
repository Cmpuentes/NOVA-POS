package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.RolRequest;
import com.gesnnova.novapos_backend.dto.RolResponse;
import com.gesnnova.novapos_backend.service.RolService;
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
@RequestMapping("/api/roles")
@Tag(name = "Roles", description = "Gestión de roles de usuario")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Listar roles activos",
            description = "Devuelve todos los roles activos. Solo ADMINISTRADOR.")
    @GetMapping
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<List<RolResponse>> listar() {
        return ResponseEntity.ok(rolService.listarTodos());
    }

    @Operation(summary = "Buscar rol por ID",
            description = "Devuelve un rol específico por su UUID. Solo ADMINISTRADOR.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<RolResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(rolService.buscarPorId(id));
    }

    @Operation(summary = "Crear rol",
            description = "Crea un nuevo rol. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<RolResponse> crear(
            @Valid @RequestBody RolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolService.crear(request));
    }

    @Operation(summary = "Actualizar rol",
            description = "Actualiza nombre y descripción de un rol. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<RolResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody RolRequest request) {
        return ResponseEntity.ok(rolService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar rol",
            description = "Desactiva un rol sin borrarlo. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        rolService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
