package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "resolucion_facturacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResolucionFacturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @Column(name = "prefijo", nullable = false)
    private String prefijo;

    @Column(name = "numero_desde", nullable = false)
    private Integer numeroDesde;

    @Column(name = "numero_hasta", nullable = false)
    private Integer numeroHasta;

    @Column(name = "numero_actual")
    private Integer numeroActual;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(name = "clave_tecnica", nullable = false)
    private String claveTecnica;

    @Column(name = "activa", nullable = false)
    private boolean activa;

    @OneToMany(mappedBy = "resolucionFacturacion")
    private java.util.List<Venta> ventas;

}
