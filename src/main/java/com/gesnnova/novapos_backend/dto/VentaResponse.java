package com.gesnnova.novapos_backend.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponse {

    private UUID id;
    private String numeroDocumento;
    private String tipoDocumento;
    private LocalDateTime fechaHora;
    private String cajaNombre;
    private String usuarioNombre;
    private String clienteNombre;
    private BigDecimal subtotal;
    private BigDecimal totalImpuestos;
    private BigDecimal totalDescuentos;
    private BigDecimal total;
    private String estado;
    private String estadoDian;
    private String cufe;
    private boolean generadoOffline;
    private List<ItemVentaResponse> items;
    private List<PagoResponse> pagos;
}
