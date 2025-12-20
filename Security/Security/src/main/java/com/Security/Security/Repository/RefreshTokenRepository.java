package com.Security.Security.Repository;

import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> { // Buscar refresh token
    // Revogar token
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
