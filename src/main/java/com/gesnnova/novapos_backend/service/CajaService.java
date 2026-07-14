package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Caja;
import com.gesnnova.novapos_backend.dto.CajaRequest;
import com.gesnnova.novapos_backend.dto.CajaResponse;
import com.gesnnova.novapos_backend.repository.CajaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CajaService {

    @Autowired
    private CajaRepository cajaRepository;

    public List<CajaResponse> listarTodas() {
        return cajaRepository.findByActivaTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CajaResponse buscarPorId(UUID id) {
        Caja caja = cajaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));
        return toResponse(caja);
    }

    public CajaResponse crear(CajaRequest request) {
        if (cajaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una caja con ese nombre");
        }
        Caja caja = Caja.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activa(true)
                .build();
        return toResponse(cajaRepository.save(caja));
    }

    public CajaResponse actualizar(UUID id, CajaRequest request) {
        Caja caja = cajaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));
        caja.setNombre(request.getNombre());
        caja.setDescripcion(request.getDescripcion());
        return toResponse(cajaRepository.save(caja));
    }

    public void desactivar(UUID id) {
        Caja caja = cajaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));
        caja.setActiva(false);
        cajaRepository.save(caja);
    }

    private CajaResponse toResponse(Caja caja) {
        return CajaResponse.builder()
                .id(caja.getId())
                .nombre(caja.getNombre())
                .descripcion(caja.getDescripcion())
                .activa(caja.isActiva())
                .build();
    }
}
