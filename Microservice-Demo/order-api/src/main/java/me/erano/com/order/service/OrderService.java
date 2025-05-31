package me.erano.com.order.service;

import me.erano.com.order.dto.OrderItemDto;
import me.erano.com.order.dto.OrderResponse;
import me.erano.com.order.model.OrderStatus;
import me.erano.com.order.model.CartItem;
import me.erano.com.order.model.Order;
import me.erano.com.order.model.OrderItem;
import me.erano.com.order.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;

    public OrderService(CartService cartService, OrderRepository orderRepository) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    public Optional<OrderResponse> createOrder(String userId) {
        //Validate cart items
        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
        //Validate user
        /*Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();*/
        //Calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setAmount(totalPrice);
        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        //Clear cart
        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getAmount(),
                order.getStatus(),
                order.getItems().stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getId(),
                                orderItem.getProductId(),
                                orderItem.getQuantity(),
                                orderItem.getUnitPrice(),
                                orderItem.getUnitPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                        ))
                        .toList(),
                order.getCreatedAt()
        );
    }

}
