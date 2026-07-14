package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private UUID id;
    private String nombre;
    private String descripcion;
    private String codigo;
    private BigDecimal precioBase;
    private String imagenUrl;
    private String categoriaNombre;
    private String impuestoNombre;
    private BigDecimal impuestoPorcentaje;
    private String codigoDian;
    private String unidadMedidaNombre;
    private String unidadMedidaAbreviatura;
    private String tipoControlStock;
    private boolean activo;
}
