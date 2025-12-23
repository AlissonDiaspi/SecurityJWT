package com.Security.Security.Dto;

public record LoginResponse( // record para enviar a resposta do login
    String accessToken,
    String refreshToken

) {}
