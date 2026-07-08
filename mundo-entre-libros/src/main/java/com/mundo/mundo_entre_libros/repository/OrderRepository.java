package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByUser_IdUserOrderByOrderDateDesc(Integer idUser);

    Optional<OrderEntity> findByIdOrderAndUser_IdUser(
            Integer idOrder,
            Integer idUser
    );

}