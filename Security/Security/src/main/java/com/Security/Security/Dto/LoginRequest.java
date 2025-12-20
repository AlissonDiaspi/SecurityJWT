package com.Security.Security.Dto;

public record LoginRequest (// classe para receber os dados de login
    String email,
    String password
) {

}
