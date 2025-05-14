package me.erano.com.dto;

import java.util.Objects;

public class CartItemRequest {

    private Long productId;
    private Integer quantity;

    public CartItemRequest() {

    }

    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItemRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        CartItemRequest that = (CartItemRequest) o;
        return Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(productId);
        result = 31 * result + Objects.hashCode(quantity);
        return result;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
