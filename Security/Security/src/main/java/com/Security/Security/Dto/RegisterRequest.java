package com.Security.Security.Dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest { // Classe para representar a requisição de registro de usuário
    private String email;
    private String password;

}
