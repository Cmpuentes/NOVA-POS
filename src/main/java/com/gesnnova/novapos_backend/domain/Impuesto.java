package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "impuesto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Impuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "porcentaje", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcentaje;

    @Column(name = "codigo_dian", nullable = false, unique = true)
    private String codigoDian;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @OneToMany(mappedBy = "impuesto")
    private java.util.List<Producto> productos;
}
