package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.WishlistItemDTO;
import com.mundo.mundo_entre_libros.dto.WishlistItemResponseDTO;
import com.mundo.mundo_entre_libros.dto.WishlistResponseDTO;
import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.model.User;
import com.mundo.mundo_entre_libros.model.Wishlist;
import com.mundo.mundo_entre_libros.model.WishlistItem;
import com.mundo.mundo_entre_libros.repository.BookRepository;
import com.mundo.mundo_entre_libros.repository.SagaRepository;
import com.mundo.mundo_entre_libros.repository.WishlistItemRepository;
import com.mundo.mundo_entre_libros.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final BookRepository bookRepository;
    private final SagaRepository sagaRepository;

    // ============================================================
    // OBTENER WISHLIST DEL USUARIO LOGUEADO
    // ============================================================

    public WishlistResponseDTO getMyWishlist(Authentication authentication) {

        User user = getUser(authentication);

        Wishlist wishlist = getOrCreateWishlist(user);

        return convertWishlistToDTO(wishlist);

    }

    // ============================================================
    // AGREGAR ITEM A WISHLIST
    // Puede ser libro o saga
    // ============================================================

    public WishlistResponseDTO addItem(
            WishlistItemDTO dto,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        validateWishlistItem(dto);

        Wishlist wishlist = getOrCreateWishlist(user);

        // =========================
        // AGREGAR LIBRO
        // =========================

        if (dto.bookId() != null) {

            boolean alreadyExists =
                    wishlistItemRepository.existsByWishlist_User_IdUserAndBook_IdBook(
                            user.getIdUser(),
                            dto.bookId()
                    );

            if (!alreadyExists) {

                Book book = bookRepository.findById(dto.bookId())
                        .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

                WishlistItem item = new WishlistItem();

                item.setWishlist(wishlist);
                item.setBook(book);
                item.setSaga(null);

                wishlistItemRepository.save(item);

            }

            return getMyWishlist(authentication);

        }

        // =========================
        // AGREGAR SAGA
        // =========================

        boolean alreadyExists =
                wishlistItemRepository.existsByWishlist_User_IdUserAndSaga_IdSaga(
                        user.getIdUser(),
                        dto.sagaId()
                );

        if (!alreadyExists) {

            Saga saga = sagaRepository.findById(dto.sagaId())
                    .orElseThrow(() -> new RuntimeException("Saga no encontrada"));

            WishlistItem item = new WishlistItem();

            item.setWishlist(wishlist);
            item.setBook(null);
            item.setSaga(saga);

            wishlistItemRepository.save(item);

        }

        return getMyWishlist(authentication);

    }

    // ============================================================
    // AGREGAR LIBRO DIRECTO
    // ============================================================

    public WishlistResponseDTO addBook(
            Integer bookId,
            Authentication authentication
    ) {

        return addItem(
                new WishlistItemDTO(bookId, null),
                authentication
        );

    }

    // ============================================================
    // AGREGAR SAGA DIRECTO
    // ============================================================

    public WishlistResponseDTO addSaga(
            Integer sagaId,
            Authentication authentication
    ) {

        return addItem(
                new WishlistItemDTO(null, sagaId),
                authentication
        );

    }

    // ============================================================
    // ELIMINAR ITEM POR ID
    // ============================================================

    public WishlistResponseDTO removeItem(
            Integer itemId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        WishlistItem item = wishlistItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item de wishlist no encontrado"));

        Integer itemUserId = item.getWishlist().getUser().getIdUser();

        if (!itemUserId.equals(user.getIdUser())) {
            throw new RuntimeException("No puedes eliminar un item que no te pertenece");
        }

        wishlistItemRepository.delete(item);

        return getMyWishlist(authentication);

    }

    // ============================================================
    // ELIMINAR LIBRO DE WISHLIST
    // ============================================================

    public WishlistResponseDTO removeBook(
            Integer bookId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        WishlistItem item =
                wishlistItemRepository
                        .findByWishlist_User_IdUserAndBook_IdBook(
                                user.getIdUser(),
                                bookId
                        )
                        .orElseThrow(() -> new RuntimeException("El libro no está en wishlist"));

        wishlistItemRepository.delete(item);

        return getMyWishlist(authentication);

    }

    // ============================================================
    // ELIMINAR SAGA DE WISHLIST
    // ============================================================

    public WishlistResponseDTO removeSaga(
            Integer sagaId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        WishlistItem item =
                wishlistItemRepository
                        .findByWishlist_User_IdUserAndSaga_IdSaga(
                                user.getIdUser(),
                                sagaId
                        )
                        .orElseThrow(() -> new RuntimeException("La saga no está en wishlist"));

        wishlistItemRepository.delete(item);

        return getMyWishlist(authentication);

    }

    // ============================================================
    // VERIFICAR SI LIBRO ESTÁ EN WISHLIST
    // ============================================================

    public boolean checkBook(
            Integer bookId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        return wishlistItemRepository.existsByWishlist_User_IdUserAndBook_IdBook(
                user.getIdUser(),
                bookId
        );

    }

    // ============================================================
    // VERIFICAR SI SAGA ESTÁ EN WISHLIST
    // ============================================================

    public boolean checkSaga(
            Integer sagaId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        return wishlistItemRepository.existsByWishlist_User_IdUserAndSaga_IdSaga(
                user.getIdUser(),
                sagaId
        );

    }

    // ============================================================
    // HELPERS
    // ============================================================

    private User getUser(Authentication authentication) {

        return (User) authentication.getPrincipal();

    }

    private Wishlist getOrCreateWishlist(User user) {

        return wishlistRepository
                .findByUser_IdUser(user.getIdUser())
                .orElseGet(() -> {

                    Wishlist wishlist = new Wishlist();

                    wishlist.setUser(user);

                    return wishlistRepository.save(wishlist);

                });

    }

    private void validateWishlistItem(WishlistItemDTO dto) {

        if (dto.bookId() == null && dto.sagaId() == null) {
            throw new RuntimeException("Debes enviar bookId o sagaId");
        }

        if (dto.bookId() != null && dto.sagaId() != null) {
            throw new RuntimeException("Solo puedes enviar bookId o sagaId, no ambos");
        }

    }

    private WishlistResponseDTO convertWishlistToDTO(Wishlist wishlist) {

        List<WishlistItem> items =
                wishlistItemRepository.findByWishlist_User_IdUser(
                        wishlist.getUser().getIdUser()
                );

        List<WishlistItemResponseDTO> itemDTOs =
                items.stream()
                        .map(this::convertItemToDTO)
                        .toList();

        return new WishlistResponseDTO(
                wishlist.getIdWishlist(),
                wishlist.getUser().getIdUser(),
                wishlist.getCreatedAt(),
                itemDTOs
        );

    }

    private WishlistItemResponseDTO convertItemToDTO(WishlistItem item) {

        if (item.getBook() != null) {

            Book book = item.getBook();

            return new WishlistItemResponseDTO(
                    item.getIdWishlistItem(),
                    "BOOK",
                    book.getIdBook(),
                    null,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPrice(),
                    book.getCoverUrl(),
                    item.getAddedAt()
            );

        }

        Saga saga = item.getSaga();

        return new WishlistItemResponseDTO(
                item.getIdWishlistItem(),
                "SAGA",
                null,
                saga.getIdSaga(),
                saga.getName(),
                saga.getAuthor(),
                saga.getPrice(),
                saga.getCoverUrl(),
                item.getAddedAt()
        );

    }

}