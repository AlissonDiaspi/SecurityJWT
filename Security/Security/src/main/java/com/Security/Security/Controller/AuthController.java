package com.Security.Security.Controller;

import com.Security.Security.Config.PasswordConfig;
import com.Security.Security.Dto.LoginRequest;
import com.Security.Security.Dto.LoginResponse;
import com.Security.Security.Entity.Role;
import com.Security.Security.Entity.User;
import com.Security.Security.Repository.UserRepository;
import com.Security.Security.Service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/login") // endpoint de login
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        // pega o UserDetails
        UserDetails user = (UserDetails) authentication.getPrincipal();

        // gera token JWT
        String token = jwtService.generateToken(authentication.getName());

        return new LoginResponse("Usuário autenticado com sucesso!");
    }
    @PostMapping("/register") // endpoint de registro de usuário
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) { // se o email ja existir
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Set.of(Role.USER));
        user.setEnabled(true);

        userRepository.save(user);

        return ResponseEntity.ok("Usuário registrado com sucesso");

    }

}
