package me.erano.com.order.controller;

import me.erano.com.order.dto.OrderResponse;
import me.erano.com.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //daha öncesinde cart oluşturman lazım, ve postmanda bu requesti yaparken headerlarda şunu belirtmen lazım:
    // X-User-ID -> 67d533476a55cf5c8124a59c
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestHeader("X-User-ID") String userId) {
        return orderService.createOrder(userId)
                .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
