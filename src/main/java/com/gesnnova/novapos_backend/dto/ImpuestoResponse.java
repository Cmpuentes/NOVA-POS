package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpuestoResponse {

    private UUID id;
    private String nombre;
    private BigDecimal porcentaje;
    private String codigoDian;
    private boolean activo;
}
