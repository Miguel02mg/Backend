package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByCart_User_IdUser(Integer idUser);

    Optional<CartItem> findByCart_User_IdUserAndBook_IdBook(
            Integer idUser,
            Integer idBook
    );

    Optional<CartItem> findByCart_User_IdUserAndSaga_IdSaga(
            Integer idUser,
            Integer idSaga
    );

    void deleteByCart_User_IdUser(Integer idUser);

}