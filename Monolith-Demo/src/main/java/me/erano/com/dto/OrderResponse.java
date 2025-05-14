package me.erano.com.dto;

import me.erano.com.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;

    public OrderResponse() {

    }

    public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus status, List<OrderItemDto> items, LocalDateTime createdAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", items=" + items +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        OrderResponse that = (OrderResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(totalAmount, that.totalAmount) && status == that.status && Objects.equals(items, that.items) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(totalAmount);
        result = 31 * result + Objects.hashCode(status);
        result = 31 * result + Objects.hashCode(items);
        result = 31 * result + Objects.hashCode(createdAt);
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
