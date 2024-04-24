package com.luiza.labs.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductResponse(
        @JsonProperty("product_id") Long id,
        //TODO: parece que isso é String
        BigDecimal value
) {
}


