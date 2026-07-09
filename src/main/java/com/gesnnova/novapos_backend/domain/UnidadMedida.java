package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "unidad_medida")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnidadMedida {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "abreviatura", nullable = false, unique = true)
    private String abreviatura;

    @OneToMany(mappedBy = "unidadMedida")
    private java.util.List<Producto> productos;
}
