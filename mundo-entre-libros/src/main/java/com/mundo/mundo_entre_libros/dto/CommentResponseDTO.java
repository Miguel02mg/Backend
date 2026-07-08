package com.mundo.mundo_entre_libros.dto;

import java.time.LocalDateTime;

public record CommentResponseDTO(

        Integer id,
        Integer postId,
        Integer userId,
        String comentario,
        String autor,
        LocalDateTime fecha

) {

}