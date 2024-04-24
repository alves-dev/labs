package com.luiza.labs.controller.response;

import com.luiza.labs.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BuildResponse {

    private BuildResponse() {
        throw new IllegalStateException("Utility class");
    }

    public static List<UserResponse> build(List<Item> items) {
        List<UserResponse> users = new ArrayList<>();

        Map<Long, List<Item>> groupedByUser = items.stream()
                .collect(Collectors.groupingBy(Item::getUserId));

        groupedByUser.forEach((key, value) -> {

            Map<Long, List<Item>> groupedByOrder = value.stream()
                    .collect(Collectors.groupingBy(Item::getOrderId));

            List<OrderResponse> orderList = groupedByOrder.entrySet()
                    .stream().map(BuildResponse::buildOrder)
                    .toList();

            users.add(new UserResponse(
                    key,
                    value.getFirst().getUserName(),
                    orderList)
            );
        });

        return users;
    }

    private static OrderResponse buildOrder(Map.Entry<Long, List<Item>> e) {
        return new OrderResponse(
                e.getKey(),
                e.getValue().getFirst().getOrderDate(),
                buildProduct(e.getValue()));
    }

    private static List<ProductResponse> buildProduct(List<Item> items) {
        return items.stream()
                .map(item -> new ProductResponse(item.getProductId(), item.getProductValue()))
                .toList();
    }
}


