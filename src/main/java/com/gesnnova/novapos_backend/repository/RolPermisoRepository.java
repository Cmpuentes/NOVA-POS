package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.RolPermiso;
import com.gesnnova.novapos_backend.domain.RolPermisoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, RolPermisoId> {

    List<RolPermiso> findByRolId(UUID rolId);

    void deleteByRolId(UUID rolId);
}
