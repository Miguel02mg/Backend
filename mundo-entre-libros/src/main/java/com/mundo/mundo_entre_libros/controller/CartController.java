package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.dto.CartItemQuantityDTO;
import com.mundo.mundo_entre_libros.dto.CartItemRequestDTO;
import com.mundo.mundo_entre_libros.dto.CartResponseDTO;
import com.mundo.mundo_entre_libros.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    // ============================================================
    // OBTENER MI CARRITO
    // ============================================================

    @GetMapping
    public CartResponseDTO getMyCart(Authentication authentication) {

        return cartService.getMyCart(authentication);

    }

    // ============================================================
    // AGREGAR ITEM GENÉRICO
    // Body:
    // { "bookId": 1, "quantity": 1 }
    // o
    // { "sagaId": 1, "quantity": 1 }
    // ============================================================

    @PostMapping("/items")
    public CartResponseDTO addItem(
            @RequestBody CartItemRequestDTO dto,
            Authentication authentication
    ) {

        return cartService.addItem(dto, authentication);

    }

    // ============================================================
    // AGREGAR LIBRO
    // ============================================================

    @PostMapping("/books/{bookId}")
    public CartResponseDTO addBook(
            @PathVariable Integer bookId,
            Authentication authentication
    ) {

        return cartService.addBookById(bookId, authentication);

    }

    // ============================================================
    // AGREGAR SAGA
    // ============================================================

    @PostMapping("/sagas/{sagaId}")
    public CartResponseDTO addSaga(
            @PathVariable Integer sagaId,
            Authentication authentication
    ) {

        return cartService.addSagaById(sagaId, authentication);

    }

    // ============================================================
    // ACTUALIZAR CANTIDAD
    // Body:
    // { "quantity": 3 }
    // ============================================================

    @PutMapping("/items/{itemId}")
    public CartResponseDTO updateQuantity(
            @PathVariable Integer itemId,
            @RequestBody CartItemQuantityDTO dto,
            Authentication authentication
    ) {

        return cartService.updateQuantity(itemId, dto, authentication);

    }

    // ============================================================
    // ELIMINAR ITEM POR ID
    // ============================================================

    @DeleteMapping("/items/{itemId}")
    public CartResponseDTO removeItem(
            @PathVariable Integer itemId,
            Authentication authentication
    ) {

        return cartService.removeItem(itemId, authentication);

    }

    // ============================================================
    // ELIMINAR LIBRO
    // ============================================================

    @DeleteMapping("/books/{bookId}")
    public CartResponseDTO removeBook(
            @PathVariable Integer bookId,
            Authentication authentication
    ) {

        return cartService.removeBook(bookId, authentication);

    }

    // ============================================================
    // ELIMINAR SAGA
    // ============================================================

    @DeleteMapping("/sagas/{sagaId}")
    public CartResponseDTO removeSaga(
            @PathVariable Integer sagaId,
            Authentication authentication
    ) {

        return cartService.removeSaga(sagaId, authentication);

    }

    // ============================================================
    // VACIAR CARRITO
    // ============================================================

    @DeleteMapping("/clear")
    public CartResponseDTO clearCart(Authentication authentication) {

        return cartService.clearCart(authentication);

    }

}