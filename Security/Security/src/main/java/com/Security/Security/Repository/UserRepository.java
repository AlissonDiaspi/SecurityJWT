package com.Security.Security.Repository;

import com.Security.Security.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface    UserRepository extends JpaRepository<User, Long> { // Buscar usuário por email (login)
    // Verificar existência (registro)

    Optional<User> findByEmail(String email); // procura usuário pelo email no banco de dados

    boolean existsByEmail(String email); // verifica a existência do email no banco de dados
}
