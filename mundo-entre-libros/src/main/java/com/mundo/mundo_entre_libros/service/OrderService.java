package com.mundo.mundo_entre_libros.service;

import com.mundo.mundo_entre_libros.dto.OrderItemResponseDTO;
import com.mundo.mundo_entre_libros.dto.OrderRequestDTO;
import com.mundo.mundo_entre_libros.dto.OrderResponseDTO;

import com.mundo.mundo_entre_libros.model.Book;
import com.mundo.mundo_entre_libros.model.Cart;
import com.mundo.mundo_entre_libros.model.CartItem;
import com.mundo.mundo_entre_libros.model.OrderEntity;
import com.mundo.mundo_entre_libros.model.OrderItem;
import com.mundo.mundo_entre_libros.model.Saga;
import com.mundo.mundo_entre_libros.model.User;

import com.mundo.mundo_entre_libros.repository.CartItemRepository;
import com.mundo.mundo_entre_libros.repository.CartRepository;
import com.mundo.mundo_entre_libros.repository.OrderItemRepository;
import com.mundo.mundo_entre_libros.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private static final Set<String> PAYMENT_METHODS = Set.of(
            "cash",
            "card",
            "transfer",
            "points"
    );

    // ============================================================
    // CREAR PEDIDO DESDE CARRITO
    // ============================================================

    public OrderResponseDTO createOrderFromCart(
            OrderRequestDTO dto,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        Cart cart = cartRepository.findByUser_IdUser(user.getIdUser())
                .orElseThrow(() -> new RuntimeException("No tienes carrito"));

        List<CartItem> cartItems =
                cartItemRepository.findByCart_User_IdUser(user.getIdUser());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        String paymentMethod = dto != null && dto.paymentMethod() != null
                ? dto.paymentMethod().toLowerCase()
                : "cash";

        if (!PAYMENT_METHODS.contains(paymentMethod)) {
            throw new RuntimeException("Método de pago inválido");
        }

        BigDecimal total = cartItems.stream()
                .map(item ->
                        item.getPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = new OrderEntity();

        order.setUser(user);
        order.setTotalAmount(total);
        order.setPaymentMethod(paymentMethod);
        order.setStatus("pending");

        OrderEntity savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(savedOrder);
            orderItem.setBook(cartItem.getBook());
            orderItem.setSaga(cartItem.getSaga());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getPrice());

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        return convertOrderToDTO(savedOrder);
    }

    // ============================================================
    // OBTENER HISTORIAL DEL USUARIO
    // ============================================================

    public List<OrderResponseDTO> getMyOrders(Authentication authentication) {

        User user = getUser(authentication);

        return orderRepository
                .findByUser_IdUserOrderByOrderDateDesc(user.getIdUser())
                .stream()
                .map(this::convertOrderToDTO)
                .toList();
    }

    // ============================================================
    // OBTENER PEDIDO POR ID
    // ============================================================

    public OrderResponseDTO getOrderById(
            Integer orderId,
            Authentication authentication
    ) {

        User user = getUser(authentication);

        OrderEntity order = orderRepository
                .findByIdOrderAndUser_IdUser(
                        orderId,
                        user.getIdUser()
                )
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return convertOrderToDTO(order);
    }

    // ============================================================
    // HELPERS
    // ============================================================

    private User getUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    private OrderResponseDTO convertOrderToDTO(OrderEntity order) {

        List<OrderItem> items =
                orderItemRepository.findByOrder_IdOrder(order.getIdOrder());

        List<OrderItemResponseDTO> itemDTOs =
                items.stream()
                        .map(this::convertItemToDTO)
                        .toList();

        Integer totalProducts =
                items.stream()
                        .mapToInt(OrderItem::getQuantity)
                        .sum();

        return new OrderResponseDTO(
                order.getIdOrder(),
                order.getUser().getIdUser(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getStatus(),
                totalProducts,
                itemDTOs
        );
    }

    private OrderItemResponseDTO convertItemToDTO(OrderItem item) {

        BigDecimal subtotal =
                item.getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

        if (item.getBook() != null) {

            Book book = item.getBook();

            return new OrderItemResponseDTO(
                    item.getIdOrderItem(),
                    "BOOK",
                    book.getIdBook(),
                    null,
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoverUrl(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    subtotal
            );
        }

        Saga saga = item.getSaga();

        return new OrderItemResponseDTO(
                item.getIdOrderItem(),
                "SAGA",
                null,
                saga.getIdSaga(),
                saga.getName(),
                saga.getAuthor(),
                saga.getCoverUrl(),
                item.getQuantity(),
                item.getUnitPrice(),
                subtotal
        );
    }
    // ============================================================
// MARCAR PEDIDO COMO PAGADO
// ============================================================

    public OrderResponseDTO payOrder(
            Integer orderId,
            Authentication authentication
    ) {
        User user = getUser(authentication);

        OrderEntity order = orderRepository
                .findByIdOrderAndUser_IdUser(orderId, user.getIdUser())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        order.setStatus("completed");

        OrderEntity savedOrder = orderRepository.save(order);

        return convertOrderToDTO(savedOrder);
    }
}