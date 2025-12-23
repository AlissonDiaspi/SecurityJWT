package com.Security.Security.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest { // Solicitação para renovar o token de acesso
    private String refreshToken; // Token de atualização fornecido pelo cliente
}
