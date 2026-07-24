package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequest {

    @NotNull(message = "La sesión de caja es obligatoria")
    private UUID sesionCajaId;

    private UUID clienteId;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotEmpty(message = "La venta debe tener al menos un ítem")
    private List<ItemVentaRequest> items;

    @NotEmpty(message = "La venta debe tener al menos un pago")
    private List<PagoRequest> pagos;
}
