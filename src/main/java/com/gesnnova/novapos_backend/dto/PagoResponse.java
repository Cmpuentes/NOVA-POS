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
public class PagoResponse {

    private UUID id;
    private String metodo;
    private BigDecimal monto;
    private String referencia;
    private LocalDateTime registradoEn;
}
