package com.Security.Security.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenResponse { // Resposta contendo o novo token de acesso e o token de atualização
    private String Token; // Novo token de acesso gerado
    private String refreshToken; // Token de atualização associado

}
