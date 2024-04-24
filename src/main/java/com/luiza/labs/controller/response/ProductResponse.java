package com.luiza.labs.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductResponse(
        @JsonProperty("product_id") Long id,
        @JsonIgnore BigDecimal value,
        @JsonProperty("value") String valueString
) {

    public ProductResponse(Long id, BigDecimal value) {
        this(id, value, value.toString());
    }
}


