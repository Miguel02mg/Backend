package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.CartItemQuantityDTO;
import com.mundo.mundo_entre_libros.dto.CartItemRequestDTO;
import com.mundo.mundo_entre_libros.dto.CartItemResponseDTO;
import com.mundo.mundo_entre_libros.dto.CartResponseDTO;

import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.model.Cart;
import com.mundo.mundo_entre_libros.model.CartItem;
import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.model.User;

import com.mundo.mundo_entre_libros.repository.BookRepository;
import com.mundo.mundo_entre_libros.repository.CartItemRepository;
import com.mundo.mundo_entre_libros.repository.CartRepository;
import com.mundo.mundo_entre_libros.repository.SagaRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final SagaRepository sagaRepository;

    // ============================================================
    // OBTENER MI CARRITO
    // ============================================================

    public CartResponseDTO getMyCart(Authentication authentication) {

        User user = getUser(authentication);

        Cart cart = getOrCreateCart(user);

        return convertCartToDTO(cart);

    }

    // ============================================================
    // AGREGAR ITEM GENÉRICO
    // Body:
    // { "bookId": 1, "quantity": 2 }
    // o
    // { "sagaId": 1, "quantity": 1 }
    // ============================================================

    public CartResponseDTO addItem(
            CartItemRequestDTO dto,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        validateItem(dto);

        Cart cart = getOrCreateCart(user);

        int quantity = dto.quantity() != null && dto.quantity() > 0
                ? dto.quantity()
                : 1;

        if (dto.bookId() != null) {
            return addBook(dto.bookId(), quantity, user, cart);
        }

        return addSaga(dto.sagaId(), quantity, user, cart);

    }

    // ============================================================
    // AGREGAR LIBRO POR ID
    // ============================================================

    public CartResponseDTO addBookById(
            Integer bookId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        Cart cart = getOrCreateCart(user);

        return addBook(bookId, 1, user, cart);

    }

    // ============================================================
    // AGREGAR SAGA POR ID
    // ============================================================

    public CartResponseDTO addSagaById(
            Integer sagaId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        Cart cart = getOrCreateCart(user);

        return addSaga(sagaId, 1, user, cart);

    }

    // ============================================================
    // ACTUALIZAR CANTIDAD
    // Si quantity <= 0, elimina el item
    // ============================================================

    public CartResponseDTO updateQuantity(
            Integer itemId,
            CartItemQuantityDTO dto,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado"));

        validateOwner(item, user);

        int quantity = dto.quantity() != null ? dto.quantity() : 1;

        if (quantity <= 0) {
            cartItemRepository.delete(item);
            return getMyCartFromUser(user);
        }

        item.setQuantity(quantity);

        cartItemRepository.save(item);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // ELIMINAR ITEM POR ID
    // ============================================================

    public CartResponseDTO removeItem(
            Integer itemId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item de carrito no encontrado"));

        validateOwner(item, user);

        cartItemRepository.delete(item);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // ELIMINAR LIBRO DEL CARRITO
    // ============================================================

    public CartResponseDTO removeBook(
            Integer bookId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        CartItem item = cartItemRepository
                .findByCart_User_IdUserAndBook_IdBook(
                        user.getIdUser(),
                        bookId
                )
                .orElseThrow(() -> new RuntimeException("El libro no está en el carrito"));

        cartItemRepository.delete(item);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // ELIMINAR SAGA DEL CARRITO
    // ============================================================

    public CartResponseDTO removeSaga(
            Integer sagaId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        CartItem item = cartItemRepository
                .findByCart_User_IdUserAndSaga_IdSaga(
                        user.getIdUser(),
                        sagaId
                )
                .orElseThrow(() -> new RuntimeException("La saga no está en el carrito"));

        cartItemRepository.delete(item);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // VACIAR CARRITO
    // ============================================================

    public CartResponseDTO clearCart(Authentication authentication) {

        User user = getUser(authentication);

        cartItemRepository.deleteByCart_User_IdUser(user.getIdUser());

        return getMyCartFromUser(user);

    }

    // ============================================================
    // LÓGICA PRIVADA - AGREGAR LIBRO
    // ============================================================

    private CartResponseDTO addBook(
            Integer bookId,
            int quantity,
            User user,
            Cart cart
    ) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        CartItem item = cartItemRepository
                .findByCart_User_IdUserAndBook_IdBook(
                        user.getIdUser(),
                        bookId
                )
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);

            return getMyCartFromUser(user);
        }

        CartItem newItem = new CartItem();

        newItem.setCart(cart);
        newItem.setBook(book);
        newItem.setSaga(null);
        newItem.setQuantity(quantity);
        newItem.setPrice(toBigDecimal(book.getPrice()));

        cartItemRepository.save(newItem);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // LÓGICA PRIVADA - AGREGAR SAGA
    // ============================================================

    private CartResponseDTO addSaga(
            Integer sagaId,
            int quantity,
            User user,
            Cart cart
    ) {

        Saga saga = sagaRepository.findById(sagaId)
                .orElseThrow(() -> new RuntimeException("Saga no encontrada"));

        CartItem item = cartItemRepository
                .findByCart_User_IdUserAndSaga_IdSaga(
                        user.getIdUser(),
                        sagaId
                )
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);

            return getMyCartFromUser(user);
        }

        CartItem newItem = new CartItem();

        newItem.setCart(cart);
        newItem.setBook(null);
        newItem.setSaga(saga);
        newItem.setQuantity(quantity);
        newItem.setPrice(toBigDecimal(saga.getPrice()));

        cartItemRepository.save(newItem);

        return getMyCartFromUser(user);

    }

    // ============================================================
    // HELPERS
    // ============================================================

    private User getUser(Authentication authentication) {

        return (User) authentication.getPrincipal();

    }

    private Cart getOrCreateCart(User user) {

        return cartRepository
                .findByUser_IdUser(user.getIdUser())
                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setUser(user);

                    return cartRepository.save(cart);

                });

    }

    private CartResponseDTO getMyCartFromUser(User user) {

        Cart cart = getOrCreateCart(user);

        return convertCartToDTO(cart);

    }

    private void validateItem(CartItemRequestDTO dto) {

        if (dto.bookId() == null && dto.sagaId() == null) {
            throw new RuntimeException("Debes enviar bookId o sagaId");
        }

        if (dto.bookId() != null && dto.sagaId() != null) {
            throw new RuntimeException("Solo puedes enviar bookId o sagaId, no ambos");
        }

    }

    private void validateOwner(CartItem item, User user) {

        Integer itemUserId = item.getCart().getUser().getIdUser();

        if (!itemUserId.equals(user.getIdUser())) {
            throw new RuntimeException("No puedes modificar un item que no te pertenece");
        }

    }

    private BigDecimal toBigDecimal(Number value) {

        if (value == null) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }

        return BigDecimal.valueOf(value.doubleValue());

    }

    // ============================================================
    // CONVERTIR CARRITO A DTO
    // ============================================================

    private CartResponseDTO convertCartToDTO(Cart cart) {

        List<CartItem> items =
                cartItemRepository.findByCart_User_IdUser(
                        cart.getUser().getIdUser()
                );

        List<CartItemResponseDTO> itemDTOs =
                items.stream()
                        .map(this::convertItemToDTO)
                        .toList();

        Integer totalProducts =
                items.stream()
                        .mapToInt(CartItem::getQuantity)
                        .sum();

        BigDecimal subtotal =
                items.stream()
                        .map(item ->
                                item.getPrice()
                                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        )
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponseDTO(
                cart.getIdCart(),
                cart.getUser().getIdUser(),
                cart.getCreatedAt(),
                totalProducts,
                subtotal,
                subtotal,
                itemDTOs
        );

    }

    private CartItemResponseDTO convertItemToDTO(CartItem item) {

        BigDecimal subtotal =
                item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

        if (item.getBook() != null) {

            Book book = item.getBook();

            return new CartItemResponseDTO(
                    item.getIdCartItem(),
                    "BOOK",
                    book.getIdBook(),
                    null,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoverUrl(),
                    item.getQuantity(),
                    item.getPrice(),
                    subtotal
            );

        }

        Saga saga = item.getSaga();

        return new CartItemResponseDTO(
                item.getIdCartItem(),
                "SAGA",
                null,
                saga.getIdSaga(),
                saga.getName(),
                saga.getAuthor(),
                saga.getCoverUrl(),
                item.getQuantity(),
                item.getPrice(),
                subtotal
        );

    }

}