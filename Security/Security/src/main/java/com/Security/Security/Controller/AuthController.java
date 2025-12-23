package com.Security.Security.Controller;

import com.Security.Security.Dto.LoginRequest;
import com.Security.Security.Dto.LoginResponse;
import com.Security.Security.Dto.RefreshTokenRequest;
import com.Security.Security.Dto.RefreshTokenResponse;
import com.Security.Security.Entity.RefreshToken;
import com.Security.Security.Entity.Role;
import com.Security.Security.Entity.User;
import com.Security.Security.Repository.RefreshTokenRepository;
import com.Security.Security.Repository.UserRepository;
import com.Security.Security.Service.JWTService;
import com.Security.Security.Service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenRepository refreshTokenRepository, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenService = refreshTokenService;
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
        User user = (User) authentication.getPrincipal();

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // gera token JWT
        String accessToken = jwtService.generateToken(authentication.getName(),roles);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);


        return new LoginResponse(accessToken,refreshToken.getToken());
    }
    @PostMapping("/register") // endpoint de registro de usuário
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        if (userRepository.findByEmail(request.getEmail()).isPresent()) { // se o email ja existir
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.USER));
        user.setEnabled(true);

        userRepository.save(user);

        return ResponseEntity.ok("Usuário registrado com sucesso");

    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) { // endpoint de refresh token
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(() -> new RuntimeException("Refresh token inválido"));
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();

        Set<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String newJwt = jwtService.generateToken(
                user.getUsername(),
                roles
        );

        // Gera novo refresh token (rotaciona)
        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(
                new RefreshTokenResponse(
                        newJwt,
                        newRefreshToken.getToken()
                )
        );



    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> requestMap) {

        String refreshToken = requestMap.get("refreshToken");
        if (refreshToken == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Refresh token não fornecido"));
        }

        try {
            refreshTokenService.revokeToken(refreshToken);
            return ResponseEntity.ok(Map.of("message", "Logout realizado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", "Erro no logout: " + e.getMessage()));
        }
    }


}
