package com.luiza.labs.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        @JsonProperty("order_id") Long id,
        //TODO: parece que isso é String
        BigDecimal total,
        LocalDate date,
        List<ProductResponse> products
) {

    public OrderResponse(Long id, LocalDate date, List<ProductResponse> products) {
        this(
                id,
                products.stream().map(ProductResponse::value).reduce(BigDecimal.ZERO, BigDecimal::add),
                date,
                products
        );
    }
}


