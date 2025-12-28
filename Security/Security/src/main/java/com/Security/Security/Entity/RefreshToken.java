package com.Security.Security.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


    @Entity
    @Table(name = "refresh_tokens")
    @Getter
    @Setter

    @NoArgsConstructor
    @AllArgsConstructor
    public class RefreshToken { // Entidade que representa o token de atualização no banco de dados

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id; // identificador único do token

        @Column(nullable = false, unique = true, length = 512) // token de atualização
        private String token;

        @OneToOne(fetch = FetchType.LAZY) // relacionamento com a entidade User
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(nullable = false) // data de expiração
        private Instant expiryDate;

        @Column(nullable = false) // indica se o token foi revogado
        private boolean revoked;
    }

