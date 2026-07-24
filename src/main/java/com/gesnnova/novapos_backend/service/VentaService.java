package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.*;
import com.gesnnova.novapos_backend.dto.*;
import com.gesnnova.novapos_backend.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ItemVentaRepository itemVentaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SesionCajaRepository sesionCajaRepository;

    @Autowired
    private ResolucionFacturacionRepository resolucionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public VentaResponse registrar(VentaRequest request, UUID usuarioId) {

        // Validar sesión de caja activa
        SesionCaja sesion = sesionCajaRepository.findById(request.getSesionCajaId())
                .orElseThrow(() -> new RuntimeException("Sesión de caja no encontrada"));

        if (!sesion.getEstado().equals("ABIERTA")) {
            throw new RuntimeException("La sesión de caja no está activa");
        }

        // Obtener cliente si viene
        Cliente cliente = null;
        if (request.getClienteId() != null) {
            cliente = clienteRepository.findById(request.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        }

        // Obtener resolución activa
        ResolucionFacturacion resolucion = resolucionRepository
                .findByActivaTrue()
                .orElseThrow(() -> new RuntimeException("No hay resolución de facturación activa"));

        // Construir ítems y calcular totales
        List<ItemVenta> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;
        BigDecimal totalDescuentos = BigDecimal.ZERO;

        for (ItemVentaRequest itemRequest : request.getItems()) {
            Producto producto = productoRepository.findById(itemRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal precioUnitario = producto.getPrecioBase();
            BigDecimal cantidad = itemRequest.getCantidad();
            BigDecimal descuento = itemRequest.getDescuento() != null ?
                    itemRequest.getDescuento() : BigDecimal.ZERO;
            BigDecimal porcentajeImpuesto = producto.getImpuesto().getPorcentaje();

            BigDecimal subtotalItem = precioUnitario.multiply(cantidad);
            BigDecimal valorImpuesto = subtotalItem
                    .multiply(porcentajeImpuesto)
                    .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            BigDecimal totalItem = subtotalItem.add(valorImpuesto).subtract(descuento);

            ItemVenta item = ItemVenta.builder()
                    .nombreProducto(producto.getNombre())
                    .precioUnitario(precioUnitario)
                    .cantidad(cantidad)
                    .unidadMedida(producto.getUnidadMedida().getAbreviatura())
                    .porcentajeImpuesto(porcentajeImpuesto)
                    .valorImpuesto(valorImpuesto)
                    .descuento(descuento)
                    .subtotal(subtotalItem)
                    .total(totalItem)
                    .producto(producto)
                    .build();

            items.add(item);
            subtotal = subtotal.add(subtotalItem);
            totalImpuestos = totalImpuestos.add(valorImpuesto);
            totalDescuentos = totalDescuentos.add(descuento);
        }

        BigDecimal total = subtotal.add(totalImpuestos).subtract(totalDescuentos);

        // Validar que los pagos cuadren con el total
        BigDecimal totalPagos = request.getPagos().stream()
                .map(PagoRequest::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPagos.compareTo(total) < 0) {
            throw new RuntimeException("El monto de los pagos no cubre el total de la venta");
        }

        // Generar número de documento
        Integer numeroActual = resolucion.getNumeroActual() == null ?
                resolucion.getNumeroDesde() : resolucion.getNumeroActual() + 1;

        if (numeroActual > resolucion.getNumeroHasta()) {
            throw new RuntimeException("Se agotó el rango de numeración de la resolución");
        }

        resolucion.setNumeroActual(numeroActual);
        String numeroDocumento = resolucion.getPrefijo() + "-" +
                String.format("%05d", numeroActual);

        // Crear la venta
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = Venta.builder()
                .sesionCaja(sesion)
                .usuario(usuario)
                .cliente(cliente)
                .resolucionFacturacion(resolucion)
                .numeroDocumento(numeroDocumento)
                .tipoDocumento(request.getTipoDocumento())
                .fechaHora(LocalDateTime.now())
                .subtotal(subtotal)
                .totalImpuestos(totalImpuestos)
                .totalDescuentos(totalDescuentos)
                .total(total)
                .estado("COMPLETADA")
                .estadoDian("PENDIENTE")
                .generadoOffline(false)
                .build();

        venta = ventaRepository.save(venta);

        // Guardar ítems vinculados a la venta
        final Venta ventaGuardada = venta;
        items.forEach(item -> item.setVenta(ventaGuardada));
        itemVentaRepository.saveAll(items);

        // Guardar pagos
        List<Pago> pagos = new ArrayList<>();
        for (PagoRequest pagoRequest : request.getPagos()) {
            Pago pago = Pago.builder()
                    .venta(ventaGuardada)
                    .metodo(pagoRequest.getMetodo())
                    .monto(pagoRequest.getMonto())
                    .referencia(pagoRequest.getReferencia())
                    .registradoEn(LocalDateTime.now())
                    .build();
            pagos.add(pago);
        }
        pagoRepository.saveAll(pagos);

        return toResponse(ventaGuardada, items, pagos);
    }

    public List<VentaResponse> listarPorSesion(UUID sesionId) {
        return ventaRepository.findBySesionCajaId(sesionId)
                .stream()
                .map(v -> toResponse(v,
                        itemVentaRepository.findByVentaId(v.getId()),
                        pagoRepository.findByVentaId(v.getId())))
                .collect(Collectors.toList());
    }

    public VentaResponse buscarPorId(UUID id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
        return toResponse(venta,
                itemVentaRepository.findByVentaId(id),
                pagoRepository.findByVentaId(id));
    }

    private VentaResponse toResponse(Venta venta,
                                     List<ItemVenta> items,
                                     List<Pago> pagos) {
        return VentaResponse.builder()
                .id(venta.getId())
                .numeroDocumento(venta.getNumeroDocumento())
                .tipoDocumento(venta.getTipoDocumento())
                .fechaHora(venta.getFechaHora())
                .cajaNombre(venta.getSesionCaja().getCaja().getNombre())
                .usuarioNombre(venta.getUsuario().getNombreCompleto())
                .clienteNombre(venta.getCliente() != null ?
                        venta.getCliente().getNombreRazonSocial() : "Consumidor final")
                .subtotal(venta.getSubtotal())
                .totalImpuestos(venta.getTotalImpuestos())
                .totalDescuentos(venta.getTotalDescuentos())
                .total(venta.getTotal())
                .estado(venta.getEstado())
                .estadoDian(venta.getEstadoDian())
                .cufe(venta.getCufe())
                .generadoOffline(venta.isGeneradoOffline())
                .items(items.stream().map(this::toItemResponse).collect(Collectors.toList()))
                .pagos(pagos.stream().map(this::toPagoResponse).collect(Collectors.toList()))
                .build();
    }

    private ItemVentaResponse toItemResponse(ItemVenta item) {
        return ItemVentaResponse.builder()
                .id(item.getId())
                .nombreProducto(item.getNombreProducto())
                .precioUnitario(item.getPrecioUnitario())
                .cantidad(item.getCantidad())
                .unidadMedida(item.getUnidadMedida())
                .porcentajeImpuesto(item.getPorcentajeImpuesto())
                .valorImpuesto(item.getValorImpuesto())
                .descuento(item.getDescuento())
                .subtotal(item.getSubtotal())
                .total(item.getTotal())
                .build();
    }

    private PagoResponse toPagoResponse(Pago pago) {
        return PagoResponse.builder()
                .id(pago.getId())
                .metodo(pago.getMetodo())
                .monto(pago.getMonto())
                .referencia(pago.getReferencia())
                .registradoEn(pago.getRegistradoEn())
                .build();
    }
}
