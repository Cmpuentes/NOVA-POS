package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "negocio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @Column(name = "departamento", nullable = false)
    private String departamento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "regimen_fiscal", nullable = false)
    private String regimenFiscal;

    @Column(name = "responsabilidad_fiscal", nullable = false)
    private String responsabilidadFiscal;

    @OneToMany(mappedBy = "negocio")
    private List<ResolucionFacturacion> resoluciones;

}
