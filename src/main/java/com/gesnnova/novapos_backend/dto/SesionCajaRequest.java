package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SesionCajaRequest {

    @NotNull(message = "La caja es obligatoria")
    private UUID cajaId;

    @NotNull(message = "El monto de apertura es obligatorio")
    private BigDecimal montoApertura;
}
