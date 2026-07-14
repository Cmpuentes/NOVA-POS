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
public class CierreCajaRequest {

    @NotNull(message = "El monto de cierre es obligatorio")
    private BigDecimal montoCierre;
}
