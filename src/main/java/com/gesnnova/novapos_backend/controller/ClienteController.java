package com.gesnnova.novapos_backend.controller;

import com.gesnnova.novapos_backend.dto.ClienteRequest;
import com.gesnnova.novapos_backend.dto.ClienteResponse;
import com.gesnnova.novapos_backend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes para facturación")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar clientes",
            description = "Devuelve todos los clientes activos.")
    @GetMapping
    @PreAuthorize("hasAuthority('CLIENTES_VER')")
    public ResponseEntity<List<ClienteResponse>> listar() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @Operation(summary = "Buscar cliente por ID",
            description = "Devuelve un cliente específico por su UUID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_VER')")
    public ResponseEntity<ClienteResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteService.buscarPorId(id));
    }

    @Operation(summary = "Buscar cliente por número de documento",
            description = "El cajero busca al cliente por cédula o NIT al momento de pedir factura.")
    @GetMapping("/documento/{numeroDocumento}")
    @PreAuthorize("hasAuthority('CLIENTES_VER')")
    public ResponseEntity<ClienteResponse> buscarPorDocumento(
            @PathVariable String numeroDocumento) {
        return ResponseEntity.ok(clienteService.buscarPorDocumento(numeroDocumento));
    }

    @Operation(summary = "Crear cliente",
            description = "Crea un nuevo cliente. Disponible para CAJERO y ADMINISTRADOR.")
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENTES_CREAR')")
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.crear(request));
    }

    @Operation(summary = "Actualizar cliente",
            description = "Actualiza los datos de un cliente. Solo ADMINISTRADOR.")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_EDITAR')")
    public ResponseEntity<ClienteResponse> actualizar(
            @PathVariable UUID id,
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(clienteService.actualizar(id, request));
    }

    @Operation(summary = "Desactivar cliente",
            description = "Desactiva un cliente sin borrarlo. Solo ADMINISTRADOR.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CLIENTES_EDITAR')")
    public ResponseEntity<Void> desactivar(@PathVariable UUID id) {
        clienteService.desactivar(id);
        return ResponseEntity.noContent().build();
    }
}
