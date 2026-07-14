package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Rol;
import com.gesnnova.novapos_backend.dto.RolRequest;
import com.gesnnova.novapos_backend.dto.RolResponse;
import com.gesnnova.novapos_backend.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public List<RolResponse> listarTodos() {
        return rolRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RolResponse buscarPorId(UUID id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return toResponse(rol);
    }

    public RolResponse crear(RolRequest request) {
        if (rolRepository.findByNombre(request.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un rol con ese nombre");
        }
        Rol rol = Rol.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(true)
                .build();
        return toResponse(rolRepository.save(rol));
    }

    public RolResponse actualizar(UUID id, RolRequest request) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setNombre(request.getNombre());
        rol.setDescripcion(request.getDescripcion());
        return toResponse(rolRepository.save(rol));
    }

    public void desactivar(UUID id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rol.setActivo(false);
        rolRepository.save(rol);
    }

    private RolResponse toResponse(Rol rol) {
        return RolResponse.builder()
                .id(rol.getId())
                .nombre(rol.getNombre())
                .descripcion(rol.getDescripcion())
                .activo(rol.isActivo())
                .build();
    }
}
