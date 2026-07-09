package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "tipo_persona", nullable = false)
    private String tipoPersona; // Natural o Jurídica

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento; // CC, NIT, CE, etc.

    @Column(name = "numero_documento", nullable = false, unique = true)
    private String numeroDocumento; // Número de documento del cliente

    @Column(name = "nombre_razon_social", nullable = false)
    private String nombreRazonSocial; // Nombre o razón social del cliente

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @OneToMany(mappedBy = "cliente")
    private java.util.List<Venta> ventas;
}
