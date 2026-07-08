package com.mundo.mundo_entre_libros.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Integer idOrderItem,
        String type,
        Integer bookId,
        Integer sagaId,
        String title,
        String author,
        String coverUrl,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}