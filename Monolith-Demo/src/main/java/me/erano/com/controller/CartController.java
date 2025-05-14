package me.erano.com.controller;

import me.erano.com.dto.CartItemRequest;
import me.erano.com.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartItemService;

    public CartController(CartService cartItemService){
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest request){
        if(!cartItemService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product Out of Stock or User not found or Product not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
