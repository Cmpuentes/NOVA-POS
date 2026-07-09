package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sesion_caja")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SesionCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "caja_id", nullable = false)
    private Caja caja;

    @ManyToOne
    @JoinColumn(name = "usuario_apertura_id", nullable = false)
    private Usuario usuarioApertura;

    @ManyToOne
    @JoinColumn(name = "usuario_cierre_id")
    private Usuario usuarioCierre;

    @Column(name = "monto_apertura", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoApertura;

    @Column(name = "monto_cierre", precision = 12, scale = 2)
    private BigDecimal montoCierre;

    @Column(name = "diferencia", precision = 12, scale = 2)
    private BigDecimal diferencia;

    @Column(name = "abierta_en", nullable = false)
    private LocalDateTime abiertaEn;

    @Column(name = "cerrada_en")
    private LocalDateTime cerradaEn;

    @Column(name = "estado", nullable = false)
    private String estado; // ABIERTA o CERRADA

    @OneToMany(mappedBy = "sesionCaja")
    private java.util.List<Venta> ventas;

}
