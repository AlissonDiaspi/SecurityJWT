package com.Security.Security.Dto;

import java.util.Set;

public record UserMeResponse ( // DTO para retornar dados do usuario logado
        Long id,
        String email,
        Set<String> roles
)
{}
