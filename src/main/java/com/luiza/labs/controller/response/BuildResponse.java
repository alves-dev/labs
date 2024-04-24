package com.luiza.labs.controller.response;

import com.luiza.labs.domain.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BuildResponse {

    public static List<UserResponse> build(List<Item> items) {
        List<UserResponse> users = new ArrayList<>();

        Map<Long, List<Item>> groupedByUser = items.stream()
                .collect(Collectors.groupingBy(Item::getUserId));

        groupedByUser.forEach((key, value) -> {

            Map<Long, List<Item>> groupedByOrder = value.stream()
                    .collect(Collectors.groupingBy(Item::getOrderId));

            List<OrderResponse> orderList = groupedByOrder.entrySet()
                    .stream().map(e ->
                            new OrderResponse(
                                    e.getKey(),
                                    e.getValue().getFirst().getOrderDate(),
                                    e.getValue().stream()
                                            .map(item ->
                                                    new ProductResponse(item.getProductId(), item.getProductValue()))
                                            .toList())
                    ).toList();

            users.add(new UserResponse(
                    key,
                    value.getFirst().getUserName(),
                    orderList)
            );
        });

        return users;
    }
}


