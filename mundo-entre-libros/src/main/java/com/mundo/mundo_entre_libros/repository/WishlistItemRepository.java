package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {

    List<WishlistItem> findByWishlist_User_IdUser(Integer idUser);

    Optional<WishlistItem> findByWishlist_User_IdUserAndBook_IdBook(
            Integer idUser,
            Integer idBook
    );

    Optional<WishlistItem> findByWishlist_User_IdUserAndSaga_IdSaga(
            Integer idUser,
            Integer idSaga
    );

    boolean existsByWishlist_User_IdUserAndBook_IdBook(
            Integer idUser,
            Integer idBook
    );

    boolean existsByWishlist_User_IdUserAndSaga_IdSaga(
            Integer idUser,
            Integer idSaga
    );
}