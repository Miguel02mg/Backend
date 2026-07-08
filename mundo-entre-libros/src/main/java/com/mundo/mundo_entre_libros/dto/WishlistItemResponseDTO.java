package com.mundo.mundo_entre_libros.dto;

import java.time.LocalDateTime;

public record WishlistItemResponseDTO(

        Integer idWishlistItem,

        String type,

        Integer bookId,

        Integer sagaId,

        String title,

        String author,

        Double price,

        String coverUrl,

        LocalDateTime addedAt

) {
}