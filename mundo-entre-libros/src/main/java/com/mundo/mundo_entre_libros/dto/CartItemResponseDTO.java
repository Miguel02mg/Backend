package com.mundo.mundo_entre_libros.dto;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Integer idCartItem,
        String type,
        Integer bookId,
        Integer sagaId,
        String title,
        String author,
        String coverUrl,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal
) {
}