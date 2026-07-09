package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @Column(name = "metodo", nullable = false)
    private String metodo; // Efectivo, Tarjeta, Transferencia, etc.

    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(name = "referencia")
    private String referencia; //Número de aprobación del datáfono u otro comprobante. Opcional

    @Column(name = "registrado_en", nullable = false)
    private LocalDateTime registradoEn;
}
