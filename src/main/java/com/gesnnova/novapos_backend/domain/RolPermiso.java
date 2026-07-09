package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol_permiso")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermiso {

    @EmbeddedId
    private RolPermisoId id;

    @ManyToOne
    @MapsId("rolId")
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne
    @MapsId("permisoId")
    @JoinColumn(name = "permiso_id", nullable = false)
    private Permiso permiso;

}
