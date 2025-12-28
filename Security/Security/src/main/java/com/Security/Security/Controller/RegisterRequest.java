package com.Security.Security.Controller;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest { // Classe para representar a requisição de registro de usuário
    private String email;
    private String password;

}
