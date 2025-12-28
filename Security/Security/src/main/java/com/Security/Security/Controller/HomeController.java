package com.Security.Security.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HomeController { // controller basico para testar se a API está rodando
    @GetMapping("/")
    public String home() {
        return "API rodando com segurança!";
    }
}
