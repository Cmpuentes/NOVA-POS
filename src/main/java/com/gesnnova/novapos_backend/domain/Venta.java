package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "sesion_caja_id", nullable = false)
    private SesionCaja sesionCaja;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "resolucion_facturacion_id", nullable = false)
    private ResolucionFacturacion resolucionFacturacion;

    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento; // Número de factura con prefijo (ej. POS-00145)

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento; // FACTURA, NOTA CREDITO, NOTA DEBITO, RECIBO, OTRO

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "subtotal", nullable = false, precision = 15, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "total_impuestos", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalImpuestos;

    @Column(name = "total_descuentos", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalDescuentos;

    @Column(name = "total", nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "estado", nullable = false)
    private String estado; // PENDIENTE, PAGADA, ANULADA

    @Column(name = "estado_dian", nullable = false)
    private String estadoDian; // PENDIENTE, ENVIADA, ACEPTADA, RECHAZADA

    @Column(name = "cufe", unique = true)
    private String cufe; // Código Único de Factura Electrónica (CUFE) generado por la DIAN

    @Column(name = "sincronizado_en")
    private LocalDateTime sincronizadoEn; // Fecha y hora en que se sincronizó con la DIAN

    @Column(name = "generado_offline", nullable = false)
    private boolean generadoOffline; // Indica si la venta fue generada en modo offline

    @OneToMany(mappedBy = "venta")
    private java.util.List<Pago> pagos;

    @OneToMany(mappedBy = "venta")
    private java.util.List<ItemVenta> itemsVenta;

}
