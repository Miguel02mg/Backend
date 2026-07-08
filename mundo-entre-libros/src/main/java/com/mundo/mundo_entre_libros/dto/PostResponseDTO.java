package com.mundo.mundo_entre_libros.dto;

import java.time.LocalDateTime;

public record PostResponseDTO(

        Integer id,
        Integer forumId,
        Integer userId,
        String titulo,
        String comentario,
        String autor,
        LocalDateTime fecha

) {

}