package com.Security.Security.Dto;

import java.time.LocalDateTime;

public record ErrorResponse( // Classe para representar a resposta de erro da API
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
)  {}

