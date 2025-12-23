package com.Security.Security.Repository;

import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> { // Buscar refresh token
    // Revogar token
    Optional<RefreshToken> findByToken(String token); // Buscar token pelo valor do token

    Optional<RefreshToken> findByUser(User user); // Buscar token pelo usuário

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.user = :user")
    void deleteByUser(User user); // Deletar token pelo usuário

    List<RefreshToken> findByExpiryDateBefore(Instant now);
    List<RefreshToken> findAllByUser(User user);

}
