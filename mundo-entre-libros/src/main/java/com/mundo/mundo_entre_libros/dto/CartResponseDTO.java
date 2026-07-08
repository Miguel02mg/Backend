package com.mundo.mundo_entre_libros.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CartResponseDTO(
        Integer idCart,
        Integer userId,
        LocalDateTime createdAt,
        Integer totalProducts,
        BigDecimal subtotal,
        BigDecimal total,
        List<CartItemResponseDTO> items
) {
}
