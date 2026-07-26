package com.gesnnova.novapos_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank(message = "El tipo de persona es obligatorio")
    private String tipoPersona;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre o razón social es obligatorio")
    private String nombreRazonSocial;

    private String email;
    private String telefono;
    private String direccion;
    private String ciudad;
}
