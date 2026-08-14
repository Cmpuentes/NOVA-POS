package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsignarPermisosRequest {

    @NotEmpty(message = "Debe enviar al menos un permiso")
    private List<UUID> permisoIds;
}
