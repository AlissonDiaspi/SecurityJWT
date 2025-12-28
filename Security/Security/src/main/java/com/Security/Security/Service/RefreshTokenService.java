package com.Security.Security.Service;

import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.User;
import com.Security.Security.Repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenService { // Serviço para gerenciar refresh tokens

    @Value("${security.jwt.refresh-expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    /**
     * Cria um novo refresh token para o usuário
     * Remove o antigo (1 por usuário)
     */
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // Remove token antigo
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Verifica se o refresh token expirou
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado. Faça login novamente.");
        }
        return token;
    }

    /**
     * Revoga um refresh token específico
     */
    @Transactional
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido ou já revogado"));
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * Revoga todos os refresh tokens de um usuário
     * Útil para logout global (opcional)
     */
    @Transactional
    public void revokeAllTokensForUser(User user) {
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUser(user);
        if (tokens.isEmpty()) {
            throw new RuntimeException("Nenhum refresh token encontrado para o usuário");
        }
        refreshTokenRepository.deleteAll(tokens);
    }

    /**
     * Deleta refresh tokens expirados do banco
     */
    @Transactional
    public void deleteExpiredTokens() {
        List<RefreshToken> expiredTokens = refreshTokenRepository.findByExpiryDateBefore(Instant.now());
        if (!expiredTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(expiredTokens);
        }
    }

}
