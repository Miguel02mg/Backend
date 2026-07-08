package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.dto.WishlistItemDTO;
import com.mundo.mundo_entre_libros.dto.WishlistResponseDTO;
import com.mundo.mundo_entre_libros.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WishlistController {

    private final WishlistService wishlistService;

    // ============================================================
    // OBTENER MI WISHLIST
    // ============================================================

    @GetMapping
    public WishlistResponseDTO getMyWishlist(Authentication authentication) {

        return wishlistService.getMyWishlist(authentication);

    }

    // ============================================================
    // AGREGAR ITEM GENÉRICO
    // Body:
    // { "bookId": 1 }
    // o
    // { "sagaId": 1 }
    // ============================================================

    @PostMapping("/items")
    public WishlistResponseDTO addItem(
            @RequestBody WishlistItemDTO dto,
            Authentication authentication
    ) {

        return wishlistService.addItem(dto, authentication);

    }

    // ============================================================
    // AGREGAR LIBRO
    // ============================================================

    @PostMapping("/books/{bookId}")
    public WishlistResponseDTO addBook(
            @PathVariable Integer bookId,
            Authentication authentication
    ) {

        return wishlistService.addBook(bookId, authentication);

    }

    // ============================================================
    // AGREGAR SAGA
    // ============================================================

    @PostMapping("/sagas/{sagaId}")
    public WishlistResponseDTO addSaga(
            @PathVariable Integer sagaId,
            Authentication authentication
    ) {

        return wishlistService.addSaga(sagaId, authentication);

    }

    // ============================================================
    // ELIMINAR ITEM POR ID
    // ============================================================

    @DeleteMapping("/items/{itemId}")
    public WishlistResponseDTO removeItem(
            @PathVariable Integer itemId,
            Authentication authentication
    ) {

        return wishlistService.removeItem(itemId, authentication);

    }

    // ============================================================
    // ELIMINAR LIBRO
    // ============================================================

    @DeleteMapping("/books/{bookId}")
    public WishlistResponseDTO removeBook(
            @PathVariable Integer bookId,
            Authentication authentication
    ) {

        return wishlistService.removeBook(bookId, authentication);

    }

    // ============================================================
    // ELIMINAR SAGA
    // ============================================================

    @DeleteMapping("/sagas/{sagaId}")
    public WishlistResponseDTO removeSaga(
            @PathVariable Integer sagaId,
            Authentication authentication
    ) {

        return wishlistService.removeSaga(sagaId, authentication);

    }

    // ============================================================
    // CHECK LIBRO
    // ============================================================

    @GetMapping("/check/book/{bookId}")
    public boolean checkBook(
            @PathVariable Integer bookId,
            Authentication authentication
    ) {

        return wishlistService.checkBook(bookId, authentication);

    }

    // ============================================================
    // CHECK SAGA
    // ============================================================

    @GetMapping("/check/saga/{sagaId}")
    public boolean checkSaga(
            @PathVariable Integer sagaId,
            Authentication authentication
    ) {

        return wishlistService.checkSaga(sagaId, authentication);

    }

}