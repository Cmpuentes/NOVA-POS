package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponse {

    private UUID id;
    private String tipoPersona;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreRazonSocial;
    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
    private boolean activo;
}
