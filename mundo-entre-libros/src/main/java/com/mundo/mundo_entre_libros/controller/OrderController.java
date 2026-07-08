package com.mundo.mundo_entre_libros.controller;

import com.mundo.mundo_entre_libros.dto.OrderRequestDTO;
import com.mundo.mundo_entre_libros.dto.OrderResponseDTO;
import com.mundo.mundo_entre_libros.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    // ============================================================
    // CREAR PEDIDO DESDE CARRITO
    // Body:
    // { "paymentMethod": "cash" }
    // ============================================================

    @PostMapping("/from-cart")
    public OrderResponseDTO createOrderFromCart(
            @RequestBody(required = false) OrderRequestDTO dto,
            Authentication authentication
    ) {

        return orderService.createOrderFromCart(dto, authentication);

    }

    // ============================================================
    // HISTORIAL DE PEDIDOS DEL USUARIO
    // ============================================================

    @GetMapping
    public List<OrderResponseDTO> getMyOrders(Authentication authentication) {

        return orderService.getMyOrders(authentication);

    }

    // ============================================================
    // DETALLE DE UN PEDIDO
    // ============================================================

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(
            @PathVariable Integer orderId,
            Authentication authentication
    ) {

        return orderService.getOrderById(orderId, authentication);

    }
    // ============================================================
// PAGAR PEDIDO
// ============================================================

    @PutMapping("/{orderId}/pay")
    public OrderResponseDTO payOrder(
            @PathVariable Integer orderId,
            Authentication authentication
    ) {
        return orderService.payOrder(orderId, authentication);
    }

}