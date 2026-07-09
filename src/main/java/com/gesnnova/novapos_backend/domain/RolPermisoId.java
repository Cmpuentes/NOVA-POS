package com.gesnnova.novapos_backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RolPermisoId implements Serializable {

    @Column(name = "rol_id")
    private UUID rolId;

    @Column(name = "permiso_id")
    private UUID permisoId;

}
