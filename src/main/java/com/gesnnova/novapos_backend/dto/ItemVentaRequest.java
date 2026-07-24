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
public class ItemVentaRequest {

    @NotNull(message ="El producto es obligatorio")
    private UUID productoId;

    @NotNull(message ="La cantidad es obligatoria")
    private BigDecimal cantidad;

    private BigDecimal descuento = BigDecimal.ZERO;
}
