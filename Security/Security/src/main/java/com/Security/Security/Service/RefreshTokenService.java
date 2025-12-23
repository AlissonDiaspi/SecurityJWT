package com.Security.Security.Service;


import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.User;
import com.Security.Security.Repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService { // Serviço para gerenciar tokens de atualização
    // cria um refresh token
    // valida um refresh token
    // revoga um refresh token
    // deleta token expirado

    @Value("${security.jwt.refresh-expiration}")
    private Long refreshTokenDurationMs;


    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Cria um novo refresh token para o usuário
    public RefreshToken createRefreshToken(User user) {

        // Remove token antigo (1 por usuário)
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
     * Valida se o refresh token expirou
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expirado. Faça login novamente.");
        }
        return token;
    }


}
