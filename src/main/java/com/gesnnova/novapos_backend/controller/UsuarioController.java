package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.UsuarioRequest;
import com.gesnnova.novapos_backend.dto.UsuarioResponse;
import com.gesnnova.novapos_backend.service.UsuarioService;
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
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Listar usuarios",
            description = "Devuelve todos los usuarios del sistema. Solo ADMINISTRADOR.")
    @GetMapping
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Operation(summary = "Buscar usuario por ID",
            description = "Devuelve un usuario específico por su UUID. Solo ADMINISTRADOR.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Crear usuario",
            description = "Crea un nuevo usuario. Solo ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<UsuarioResponse> crear(
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crear(request));
    }

    @Operation(summary = "Actualizar usuario",
            description = "Actualiza los datos de un usuario. Si se envía password se actualiza, si no se deja igual. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar usuario",
            description = "Desactiva un usuario sin borrarlo. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        usuarioService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Resetear contraseña",
            description = "El administrador resetea la contraseña de cualquier usuario. Solo ADMINISTRADOR.")
    @PatchMapping("/{id}/reset-password")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    public ResponseEntity<Void> resetearPassword(
            @PathVariable UUID id,
            @RequestParam String nuevaPassword) {
        usuarioService.resetearPassword(id, nuevaPassword);
        return ResponseEntity.noContent().build();
    }
}
