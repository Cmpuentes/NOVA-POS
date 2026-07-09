package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "item_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "precio_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "cantidad", nullable = false, precision = 12, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "unidad_medida", nullable = false)
    private String unidadMedida;

    @Column(name = "porcentaje_impuesto", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentajeImpuesto;

    @Column(name = "valor_impuesto", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorImpuesto;

    @Column(name = "descuento", nullable = false, precision = 12, scale = 2)
    private BigDecimal descuento;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;
}
