package com.luiza.labs.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserResponse(
        @JsonProperty("user_id") Long id,
        String name,
        List<OrderResponse> orders
) {
}

