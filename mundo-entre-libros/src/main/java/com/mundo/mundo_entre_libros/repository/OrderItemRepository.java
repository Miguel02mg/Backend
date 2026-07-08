package com.mundo.mundo_entre_libros.repository;

import com.mundo.mundo_entre_libros.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrder_IdOrder(Integer idOrder);

}