package me.erano.com.order.service;

import jakarta.transaction.Transactional;
import me.erano.com.order.dto.CartItemRequest;
import me.erano.com.order.model.CartItem;
import me.erano.com.order.repository.CartItemRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    //private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    //private final UserRepository userRepository;

    /*public CartService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }*/

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public boolean addToCart(String userId, CartItemRequest request) {
        // Look for product
        /*Optional<Product> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt = userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty())
            return false;

        User user = userOpt.get();
        */
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, request.getProductId());
        if (existingCartItem != null) {
            // Update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(request.getQuantity()));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId,productId);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
