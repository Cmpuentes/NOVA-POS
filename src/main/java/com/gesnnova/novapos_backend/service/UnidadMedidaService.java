package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.UnidadMedida;
import com.gesnnova.novapos_backend.dto.UnidadMedidaRequest;
import com.gesnnova.novapos_backend.dto.UnidadMedidaResponse;
import com.gesnnova.novapos_backend.repository.UnidadMedidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    public List<UnidadMedidaResponse> listarTodas() {
        return unidadMedidaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UnidadMedidaResponse buscarPorId(UUID id) {
        UnidadMedida unidad = unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));
        return toResponse(unidad);
    }

    public UnidadMedidaResponse crear(UnidadMedidaRequest request) {
        if (unidadMedidaRepository.existsByAbreviatura(request.getAbreviatura())) {
            throw new RuntimeException("Ya existe una unidad con esa abreviatura");
        }
        UnidadMedida unidad = UnidadMedida.builder()
                .nombre(request.getNombre())
                .abreviatura(request.getAbreviatura())
                .build();
        return toResponse(unidadMedidaRepository.save(unidad));
    }

    public UnidadMedidaResponse actualizar(UUID id, UnidadMedidaRequest request) {
        UnidadMedida unidad = unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));
        unidad.setNombre(request.getNombre());
        unidad.setAbreviatura(request.getAbreviatura());
        return toResponse(unidadMedidaRepository.save(unidad));
    }

    public void eliminar(UUID id) {
        UnidadMedida unidad = unidadMedidaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));
        unidadMedidaRepository.delete(unidad);
    }

    private UnidadMedidaResponse toResponse(UnidadMedida unidad) {
        return UnidadMedidaResponse.builder()
                .id(unidad.getId())
                .nombre(unidad.getNombre())
                .abreviatura(unidad.getAbreviatura())
                .build();
    }

}
