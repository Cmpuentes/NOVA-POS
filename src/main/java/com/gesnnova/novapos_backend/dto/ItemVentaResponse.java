package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVentaResponse {

    private UUID id;
    private String nombreProducto;
    private BigDecimal precioUnitario;
    private BigDecimal cantidad;
    private String unidadMedida;
    private BigDecimal porcentajeImpuesto;
    private BigDecimal valorImpuesto;
    private BigDecimal descuento;
    private BigDecimal subtotal;
    private BigDecimal total;
}
