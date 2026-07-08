package com.mundo.mundo_entre_libros.dto;

import com.mundo.mundo_entre_libros.model.User;

public record LoginResponse(
        String token,
        User user
) {
}