package com.mundo.mundo_entre_libros.dto;

import java.time.LocalDateTime;
import java.util.List;

public record WishlistResponseDTO(

        Integer idWishlist,

        Integer userId,

        LocalDateTime createdAt,

        List<WishlistItemResponseDTO> items

) {
}