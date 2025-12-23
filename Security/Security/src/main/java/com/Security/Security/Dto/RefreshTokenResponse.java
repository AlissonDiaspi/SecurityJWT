package com.Security.Security.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenResponse {
    private String Token; // Novo token de acesso gerado
    private String refreshToken; // Token de atualização associado

}
