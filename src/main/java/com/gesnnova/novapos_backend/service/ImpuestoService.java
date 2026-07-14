package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Impuesto;
import com.gesnnova.novapos_backend.dto.ImpuestoRequest;
import com.gesnnova.novapos_backend.dto.ImpuestoResponse;
import com.gesnnova.novapos_backend.repository.ImpuestoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImpuestoService {

    @Autowired
    private ImpuestoRepository impuestoRepository;

    public List<ImpuestoResponse> listarTodos() {
        return impuestoRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ImpuestoResponse buscarPorId(UUID id) {
        Impuesto impuesto = impuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));
        return toResponse(impuesto);
    }

    public ImpuestoResponse crear(ImpuestoRequest request) {
        if (impuestoRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe un impuesto con ese nombre");
        }
        if (impuestoRepository.existsByCodigoDian(request.getCodigoDian())) {
            throw new RuntimeException("Ya existe un impuesto con ese código DIAN");
        }
        Impuesto impuesto = Impuesto.builder()
                .nombre(request.getNombre())
                .porcentaje(request.getPorcentaje())
                .codigoDian(request.getCodigoDian())
                .activo(true)
                .build();
        return toResponse(impuestoRepository.save(impuesto));
    }

    public ImpuestoResponse actualizar(UUID id, ImpuestoRequest request) {
        Impuesto impuesto = impuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));
        impuesto.setNombre(request.getNombre());
        impuesto.setPorcentaje(request.getPorcentaje());
        impuesto.setCodigoDian(request.getCodigoDian());
        return toResponse(impuestoRepository.save(impuesto));
    }

    public void desactivar(UUID id) {
        Impuesto impuesto = impuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));
        impuesto.setActivo(false);
        impuestoRepository.save(impuesto);
    }

    private ImpuestoResponse toResponse(Impuesto impuesto) {
        return ImpuestoResponse.builder()
                .id(impuesto.getId())
                .nombre(impuesto.getNombre())
                .porcentaje(impuesto.getPorcentaje())
                .codigoDian(impuesto.getCodigoDian())
                .activo(impuesto.isActivo())
                .build();
    }
}
