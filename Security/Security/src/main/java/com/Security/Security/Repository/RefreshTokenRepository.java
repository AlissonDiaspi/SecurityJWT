package com.Security.Security.Repository;

import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> { // Buscar refresh token
    // Revogar token
    Optional<RefreshToken> findByToken(String token); // Buscar token pelo valor do token

    Optional<RefreshToken> findByUser(User user); // Buscar token pelo usuário

    void deleteByUser(User user); // Deletar token pelo usuário
}
