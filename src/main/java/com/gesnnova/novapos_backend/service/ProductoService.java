package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Categoria;
import com.gesnnova.novapos_backend.domain.Impuesto;
import com.gesnnova.novapos_backend.domain.Producto;
import com.gesnnova.novapos_backend.domain.UnidadMedida;
import com.gesnnova.novapos_backend.dto.ProductoRequest;
import com.gesnnova.novapos_backend.dto.ProductoResponse;
import com.gesnnova.novapos_backend.repository.CategoriaRepository;
import com.gesnnova.novapos_backend.repository.ImpuestoRepository;
import com.gesnnova.novapos_backend.repository.ProductoRepository;
import com.gesnnova.novapos_backend.repository.UnidadMedidaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImpuestoRepository impuestoRepository;

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    public List<ProductoResponse> listarTodos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<ProductoResponse> listarPorCategoria(UUID categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponse buscarPorId(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(producto);
    }

    public ProductoResponse buscarPorCodigo(String codigo) {
        Producto producto = productoRepository.findByCodigoAndActivoTrue(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(producto);
    }

    public ProductoResponse crear(ProductoRequest request) {
        if (request.getCodigo() != null &&
                productoRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Impuesto impuesto = impuestoRepository.findById(request.getImpuestoId())
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(request.getUnidadMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));

        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .codigo(request.getCodigo())
                .precioBase(request.getPrecioBase())
                .imagenUrl(request.getImagenUrl())
                .categoria(categoria)
                .impuesto(impuesto)
                .unidadMedida(unidadMedida)
                .tipoControlStock("NINGUNO")
                .activo(true)
                .build();

        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse actualizar(UUID id, ProductoRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Impuesto impuesto = impuestoRepository.findById(request.getImpuestoId())
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado"));

        UnidadMedida unidadMedida = unidadMedidaRepository.findById(request.getUnidadMedidaId())
                .orElseThrow(() -> new RuntimeException("Unidad de medida no encontrada"));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setCodigo(request.getCodigo());
        producto.setPrecioBase(request.getPrecioBase());
        producto.setImagenUrl(request.getImagenUrl());
        producto.setCategoria(categoria);
        producto.setImpuesto(impuesto);
        producto.setUnidadMedida(unidadMedida);

        return toResponse(productoRepository.save(producto));
    }

    public void desactivar(UUID id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    private ProductoResponse toResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .codigo(producto.getCodigo())
                .precioBase(producto.getPrecioBase())
                .imagenUrl(producto.getImagenUrl())
                .categoriaNombre(producto.getCategoria().getNombre())
                .impuestoNombre(producto.getImpuesto().getNombre())
                .impuestoPorcentaje(producto.getImpuesto().getPorcentaje())
                .codigoDian(producto.getImpuesto().getCodigoDian())
                .unidadMedidaNombre(producto.getUnidadMedida().getNombre())
                .unidadMedidaAbreviatura(producto.getUnidadMedida().getAbreviatura())
                .tipoControlStock(producto.getTipoControlStock())
                .activo(producto.isActivo())
                .build();
    }
}
