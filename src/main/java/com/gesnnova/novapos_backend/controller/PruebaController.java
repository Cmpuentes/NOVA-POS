package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.config.JwtUtil;
import com.gesnnova.novapos_backend.config.TenantContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador de prueba para verificar el tenant activo.
 */

@RestController
@RequestMapping("/api/prueba")
public class PruebaController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/hash")
    public String generarHash() {
        return passwordEncoder.encode("Admin123*");
    }

    @GetMapping("/tenant")
    public String getTenant() {
        return "Tenant activo: " + TenantContext.getTenantId();
    }

    @GetMapping("/token")
    public String getTokenPrueba() {
        return jwtUtil.generateToken(
                "cajero@panaderia.com",
                "panaderia_prueba",
                "CAJERO",
                List.of("VENTAS_CREAR", "VENTAS_VER", "CAJA_ABRIR", "CAJA_CERRAR",
                        "PRODUCTOS_VER", "CLIENTES_VER", "CLIENTES_CREAR")
        );
    }
}
