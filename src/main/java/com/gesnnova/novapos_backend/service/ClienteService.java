package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Cliente;
import com.gesnnova.novapos_backend.dto.ClienteRequest;
import com.gesnnova.novapos_backend.dto.ClienteResponse;
import com.gesnnova.novapos_backend.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ClienteResponse buscarPorId(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return toResponse(cliente);
    }

    public ClienteResponse buscarPorDocumento(String numeroDocumento) {
        Cliente cliente = clienteRepository.findByNumeroDocumento(numeroDocumento)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return toResponse(cliente);
    }

    public ClienteResponse crear(ClienteRequest request) {
        if (clienteRepository.existsByNumeroDocumento(request.getNumeroDocumento())) {
            throw new RuntimeException("Ya existe un cliente con ese número de documento");
        }
        Cliente cliente = Cliente.builder()
                .tipoPersona(request.getTipoPersona())
                .tipoDocumento(request.getTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .nombreRazonSocial(request.getNombreRazonSocial())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .ciudad(request.getCiudad())
                .activo(true)
                .build();
        return toResponse(clienteRepository.save(cliente));
    }

    public ClienteResponse actualizar(UUID id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setTipoPersona(request.getTipoPersona());
        cliente.setTipoDocumento(request.getTipoDocumento());
        cliente.setNumeroDocumento(request.getNumeroDocumento());
        cliente.setNombreRazonSocial(request.getNombreRazonSocial());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        cliente.setCiudad(request.getCiudad());
        return toResponse(clienteRepository.save(cliente));
    }

    public void desactivar(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    private ClienteResponse toResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .tipoPersona(cliente.getTipoPersona())
                .tipoDocumento(cliente.getTipoDocumento())
                .numeroDocumento(cliente.getNumeroDocumento())
                .nombreRazonSocial(cliente.getNombreRazonSocial())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .ciudad(cliente.getCiudad())
                .activo(cliente.isActivo())
                .build();
    }
}
