package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private UUID id;
    private String nombreCompleto;
    private String email;
    private String rolNombre;
    private boolean activo;
    private LocalDateTime ultimoAcceso;
    private LocalDateTime createdAt;
}
