package com.mundo.mundo_entre_libros.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Integer idOrder,
        Integer userId,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String paymentMethod,
        String status,
        Integer totalProducts,
        List<OrderItemResponseDTO> items
) {

}