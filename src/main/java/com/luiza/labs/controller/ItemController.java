package com.luiza.labs.controller;

import com.luiza.labs.controller.response.BuildResponse;
import com.luiza.labs.controller.response.UserResponse;
import com.luiza.labs.domain.Item;
import com.luiza.labs.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Retorna uma lista de items",
            description = "Caso não seja informado um filtro todos os registros são retornados",
            tags = {"Order"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listado"),
            })
    @GetMapping
    public ResponseEntity<List<UserResponse>> list() {
        List<Item> items = itemService.findAll();
        items = itemService.findByOrderId(10L);
        return ResponseEntity.ok().body(BuildResponse.build(items));
    }
}
