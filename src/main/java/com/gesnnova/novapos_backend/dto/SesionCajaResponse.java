package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionCajaResponse {

    private UUID id;
    private String cajaNombre;
    private String usuarioAperturaNombre;
    private String usuarioCierreNombre;
    private BigDecimal montoApertura;
    private BigDecimal montoCierre;
    private BigDecimal diferencia;
    private LocalDateTime abiertaEn;
    private LocalDateTime cerradaEn;
    private String estado;
}
