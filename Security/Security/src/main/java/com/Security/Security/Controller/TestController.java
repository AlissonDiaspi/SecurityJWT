package com.Security.Security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/protected")
    public String protectedEndpoint() { // endpoint utilizado para testar se o jwt está funcionando
        return "Você acessou um endpoint PROTEGIDO "; // utilizo o token para acessar esse endpoint que não está em permitAll
    }
}
