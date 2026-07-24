package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {

    @NotNull(message = "El monto de pago es obligatorio")
    private String metodo;

    @NotNull(message = "El monto es obligatorio")
    private BigDecimal monto;

    private String referencia;
}
