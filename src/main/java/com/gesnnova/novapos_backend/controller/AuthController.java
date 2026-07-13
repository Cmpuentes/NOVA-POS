package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.AuthResponse;
import com.gesnnova.novapos_backend.dto.LoginRequest;
import com.gesnnova.novapos_backend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints para login, refresh y logout")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Iniciar sesión",
            description = "Recibe email y contraseña, devuelve Access Token y Refresh Token")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Renovar Access Token",
            description = "Envía el Refresh Token en el header 'Refresh-Token' y obtiene un nuevo Access Token")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cerrar sesión",
            description = "Invalida el Refresh Token. El frontend debe eliminar ambos tokens del almacenamiento local")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Refresh-Token") String refreshToken) {
        authService.logout(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
