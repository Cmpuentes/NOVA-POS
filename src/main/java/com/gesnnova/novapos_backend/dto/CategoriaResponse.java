package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponse {

    private UUID id;
    private String nombre;
    private String descripcion;
    private boolean activa;
}
