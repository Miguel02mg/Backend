package com.mundo.mundo_entre_libros.dto;

public record CartItemRequestDTO(
        Integer bookId,
        Integer sagaId,
        Integer quantity
) {
}