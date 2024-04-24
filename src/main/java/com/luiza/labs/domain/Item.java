package com.luiza.labs.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "item")
public class Item {

    private final Long userId;
    private final String userName;
    private final Long orderId;
    private final Long productId;
    private final BigDecimal productValue;
    private final LocalDate orderDate;

    public Item(Long userId, String userName, Long orderId, Long productId, BigDecimal productValue, LocalDate orderDate) {
        this.userId = userId;
        this.userName = userName;
        this.orderId = orderId;
        this.productId = productId;
        this.productValue = productValue;
        this.orderDate = orderDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getProductValue() {
        return productValue;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
