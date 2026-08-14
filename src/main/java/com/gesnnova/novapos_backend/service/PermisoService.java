package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.domain.Permiso;
import com.gesnnova.novapos_backend.domain.Rol;
import com.gesnnova.novapos_backend.domain.RolPermiso;
import com.gesnnova.novapos_backend.domain.RolPermisoId;
import com.gesnnova.novapos_backend.dto.AsignarPermisosRequest;
import com.gesnnova.novapos_backend.dto.PermisoResponse;
import com.gesnnova.novapos_backend.repository.PermisoRepository;
import com.gesnnova.novapos_backend.repository.RolPermisoRepository;
import com.gesnnova.novapos_backend.repository.RolRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    public List<PermisoResponse> listarTodos() {
        return permisoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<PermisoResponse> listarPorRol(UUID rolId) {
        return rolPermisoRepository.findByRolId(rolId)
                .stream()
                .map(rp -> toResponse(rp.getPermiso()))
                .collect(Collectors.toList());
    }

    public void asignarPermisos(UUID rolId, AsignarPermisosRequest request) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Eliminar permisos actuales del rol
        List<RolPermiso> permisosActuales = rolPermisoRepository.findByRolId(rolId);
        rolPermisoRepository.deleteAll(permisosActuales);

        // Asignar los nuevos permisos
        List<RolPermiso> nuevosPermisos = new ArrayList<>();
        for (UUID permisoId : request.getPermisoIds()) {
            Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new RuntimeException("Permiso no encontrado: " + permisoId));

            RolPermisoId rolPermisoId = new RolPermisoId();
            rolPermisoId.setRolId(rolId);
            rolPermisoId.setPermisoId(permisoId);

            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setId(rolPermisoId);
            rolPermiso.setRol(rol);
            rolPermiso.setPermiso(permiso);

            nuevosPermisos.add(rolPermiso);
        }
        rolPermisoRepository.saveAll(nuevosPermisos);
    }

    public void quitarPermiso(UUID rolId, UUID permisoId) {
        RolPermisoId rolPermisoId = new RolPermisoId();
        rolPermisoId.setRolId(rolId);
        rolPermisoId.setPermisoId(permisoId);

        RolPermiso rolPermiso = rolPermisoRepository.findById(rolPermisoId)
                .orElseThrow(() -> new RuntimeException("El rol no tiene ese permiso"));

        rolPermisoRepository.delete(rolPermiso);
    }

    private PermisoResponse toResponse(Permiso permiso) {
        return PermisoResponse.builder()
                .id(permiso.getId())
                .codigo(permiso.getCodigo())
                .descripcion(permiso.getDescripcion())
                .build();
    }

}
