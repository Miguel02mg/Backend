package com.mundo.mundo_entre_libros.dto;

public record LoginRequest(
        String email,
        String password
) {
}