package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Caja;
import com.gesnnova.novapos_backend.domain.SesionCaja;
import com.gesnnova.novapos_backend.domain.Usuario;
import com.gesnnova.novapos_backend.dto.CierreCajaRequest;
import com.gesnnova.novapos_backend.dto.SesionCajaRequest;
import com.gesnnova.novapos_backend.dto.SesionCajaResponse;
import com.gesnnova.novapos_backend.repository.CajaRepository;
import com.gesnnova.novapos_backend.repository.SesionCajaRepository;
import com.gesnnova.novapos_backend.repository.UsuarioRepository;
import com.gesnnova.novapos_backend.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SesionCajaService {

    @Autowired
    private SesionCajaRepository sesionCajaRepository;

    @Autowired
    private CajaRepository cajaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public SesionCajaResponse abrir(SesionCajaRequest request, UUID usuarioId) {

        if (sesionCajaRepository.existsByCajaIdAndEstado(request.getCajaId(), "ABIERTA")) {
            throw new RuntimeException("Esta caja ya tiene una sesión abierta");
        }

        Caja caja = cajaRepository.findById(request.getCajaId())
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        SesionCaja sesion = SesionCaja.builder()
                .caja(caja)
                .usuarioApertura(usuario)
                .montoApertura(request.getMontoApertura())
                .abiertaEn(LocalDateTime.now())
                .estado("ABIERTA")
                .build();

        return toResponse(sesionCajaRepository.save(sesion));
    }

    public SesionCajaResponse cerrar(UUID sesionId, CierreCajaRequest request, UUID usuarioId) {

        SesionCaja sesion = sesionCajaRepository.findById(sesionId)
                .orElseThrow(() -> new RuntimeException("Sesión no encontrada"));

        if (!sesion.getEstado().equals("ABIERTA")) {
            throw new RuntimeException("La sesión ya está cerrada");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Calcular efectivo esperado correctamente
        BigDecimal ventasEfectivo = ventaRepository
                .sumVentasEfectivoBySesion(sesion.getId());

        BigDecimal efectivoEsperado = sesion.getMontoApertura()
                .add(ventasEfectivo != null ? ventasEfectivo : BigDecimal.ZERO);

        BigDecimal diferencia = request.getMontoCierre().subtract(efectivoEsperado);

        sesion.setUsuarioCierre(usuario);
        sesion.setMontoCierre(request.getMontoCierre());
        sesion.setDiferencia(diferencia);
        sesion.setCerradaEn(LocalDateTime.now());
        sesion.setEstado("CERRADA");

        return toResponse(sesionCajaRepository.save(sesion));
    }

    public SesionCajaResponse buscarSesionActiva(UUID cajaId) {
        SesionCaja sesion = sesionCajaRepository
                .findByCajaIdAndEstado(cajaId, "ABIERTA")
                .orElseThrow(() -> new RuntimeException("No hay sesión activa para esta caja"));
        return toResponse(sesion);
    }

    public List<SesionCajaResponse> listarPorUsuario(UUID usuarioId) {
        return sesionCajaRepository.findByUsuarioAperturaId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private SesionCajaResponse toResponse(SesionCaja sesion) {
        return SesionCajaResponse.builder()
                .id(sesion.getId())
                .cajaNombre(sesion.getCaja().getNombre())
                .usuarioAperturaNombre(sesion.getUsuarioApertura().getNombreCompleto())
                .usuarioCierreNombre(sesion.getUsuarioCierre() != null ?
                        sesion.getUsuarioCierre().getNombreCompleto() : null)
                .montoApertura(sesion.getMontoApertura())
                .montoCierre(sesion.getMontoCierre())
                .diferencia(sesion.getDiferencia())
                .abiertaEn(sesion.getAbiertaEn())
                .cerradaEn(sesion.getCerradaEn())
                .estado(sesion.getEstado())
                .build();
    }
}
