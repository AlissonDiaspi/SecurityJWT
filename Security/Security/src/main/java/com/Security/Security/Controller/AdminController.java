package com.Security.Security.Controller;

import com.Security.Security.Entity.User;
import com.Security.Security.Repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController { // endpoint para testar se as roles estavam acessando as suas respectivas rotas

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Apenas ADMIN pode acessar
    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "ACESSO ADMIN OK";
    }
    }



