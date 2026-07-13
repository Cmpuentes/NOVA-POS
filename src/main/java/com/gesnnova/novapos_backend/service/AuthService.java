package com.gesnnova.novapos_backend.service;

import com.gesnnova.novapos_backend.config.JwtUtil;
import com.gesnnova.novapos_backend.config.TenantContext;
import com.gesnnova.novapos_backend.domain.RefreshToken;
import com.gesnnova.novapos_backend.domain.Usuario;
import com.gesnnova.novapos_backend.dto.AuthResponse;
import com.gesnnova.novapos_backend.dto.LoginRequest;
import com.gesnnova.novapos_backend.repository.RefreshTokenRepository;
import com.gesnnova.novapos_backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {

        // Buscar usuario por email
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        // Verificar que el usuario esté activo
        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Invalidar tokens anteriores del usuario
        List<RefreshToken> tokensAnteriores = refreshTokenRepository
                .findByUsuarioAndActivo(usuario, true);
        tokensAnteriores.forEach(t -> t.setActivo(false));
        refreshTokenRepository.saveAll(tokensAnteriores);

        // Generar tokens
        String tenantId = TenantContext.getTenantId();
        String accessToken = jwtUtil.generateToken(
                usuario.getEmail(),
                tenantId,
                usuario.getRol().getNombre()
        );

        // Crear y guardar Refresh Token
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .usuario(usuario)
                .expiracion(LocalDateTime.now().plusHours(8))
                .activo(true)
                .build();
        refreshTokenRepository.save(refreshToken);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .email(usuario.getEmail())
                .rol(usuario.getRol().getNombre())
                .tenantId(tenantId)
                .build();
    }

    public AuthResponse refresh(String refreshTokenValue) {

        // Buscar el refresh token
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));

        // Verificar que esté activo y no expirado
        if (!refreshToken.isActivo()) {
            throw new RuntimeException("Refresh token inactivo");
        }

        if (refreshToken.getExpiracion().isBefore(LocalDateTime.now())) {
            refreshToken.setActivo(false);
            refreshTokenRepository.save(refreshToken);
            throw new RuntimeException("Refresh token expirado");
        }

        // Generar nuevo Access Token
        Usuario usuario = refreshToken.getUsuario();
        String tenantId = TenantContext.getTenantId();
        String nuevoAccessToken = jwtUtil.generateToken(
                usuario.getEmail(),
                tenantId,
                usuario.getRol().getNombre()
        );

        return AuthResponse.builder()
                .accessToken(nuevoAccessToken)
                .refreshToken(refreshTokenValue)
                .email(usuario.getEmail())
                .rol(usuario.getRol().getNombre())
                .tenantId(tenantId)
                .build();
    }

    public void logout(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Token inválido"));
        refreshToken.setActivo(false);
        refreshTokenRepository.save(refreshToken);
    }
}
