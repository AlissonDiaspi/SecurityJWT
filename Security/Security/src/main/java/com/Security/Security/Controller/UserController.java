package com.Security.Security.Controller;


import com.Security.Security.Dto.UserMeResponse;
import com.Security.Security.Entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController { // controller para fornecer dados do usuario logado
    @GetMapping("/me")
    public UserMeResponse me(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return new UserMeResponse(
                user.getId(),
                user.getEmail(),
                user.getRoles()
                        .stream()
                        .map(Enum::name)
                        .collect(java.util.stream.Collectors.toSet())
        );
    }
}
