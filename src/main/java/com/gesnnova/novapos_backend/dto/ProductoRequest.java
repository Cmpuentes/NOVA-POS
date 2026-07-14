package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ProductoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    private String codigo;

    @NotNull(message = "El precio base es obligatorio")
    private BigDecimal precioBase;

    private String imagenUrl;

    @NotNull(message = "La categoría es obligatoria")
    private UUID categoriaId;

    @NotNull(message = "El impuesto es obligatorio")
    private UUID impuestoId;

    @NotNull(message = "La unidad de medida es obligatoria")
    private UUID unidadMedidaId;
}
