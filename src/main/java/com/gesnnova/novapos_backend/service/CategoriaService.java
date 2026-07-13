package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Categoria;
import com.gesnnova.novapos_backend.dto.CategoriaRequest;
import com.gesnnova.novapos_backend.dto.CategoriaResponse;
import com.gesnnova.novapos_backend.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<CategoriaResponse> listarTodas() {
        return categoriaRepository.findByActivaTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoriaResponse buscarPorId(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return toResponse(categoria);
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        if (categoriaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = Categoria.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activa(true)
                .build();
        return toResponse(categoriaRepository.save(categoria));
    }

    public CategoriaResponse actualizar(UUID id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        return toResponse(categoriaRepository.save(categoria));
    }

    public void desactivar(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setActiva(false);
        categoriaRepository.save(categoria);
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .activa(categoria.isActiva())
                .build();
    }
}
