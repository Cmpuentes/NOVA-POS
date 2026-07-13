package com.gesnnova.novapos_backend.repository;

import com.gesnnova.novapos_backend.domain.RefreshToken;
import com.gesnnova.novapos_backend.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUsuarioAndActivo(Usuario usuario, boolean activo);

    void deleteByUsuario(Usuario usuario);
}
