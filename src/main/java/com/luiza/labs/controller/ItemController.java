package com.luiza.labs.controller;

import com.luiza.labs.controller.response.BuildResponse;
import com.luiza.labs.controller.response.UserResponse;
import com.luiza.labs.domain.Item;
import com.luiza.labs.domain.exception.FilterException;
import com.luiza.labs.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/item")
public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Retorna uma lista de items",
            description = "Caso não seja informado um filtro todos os registros são retornados",
            tags = {"Order"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Invalid filter",
                            content = @Content(mediaType = "application/problem+json",
                                    schema = @Schema(implementation = ProblemDetail.class)))
            })
    @GetMapping
    public ResponseEntity<List<UserResponse>> list(
            @RequestParam(value = "order_id", required = false) Long orderId,
            @RequestParam(value = "start", required = false) LocalDate start,
            @RequestParam(value = "end", required = false) LocalDate end
    ) throws FilterException {
        if (orderId != null && orderId < 0) {
            throw new FilterException("orderId filter value is invalid");
        }
        if ((start != null && end == null) || (end != null && start == null)) {
            throw new FilterException("To use the date filter, both dates must be entered");
        }
        if (start != null && start.isAfter(end)) {
            throw new FilterException("start filter value is invalid");
        }

        List<Item> items;
        StringBuilder filter = new StringBuilder();

        if (orderId != null) {
            filter.append("orderId=").append(orderId);
            items = itemService.findByOrderId(orderId);
        } else if (start != null) {
            filter.append("date=").append(start).append("&").append(end);
            items = itemService.findByOrderDateBetween(start, end);
        } else {
            filter.append("none");
            items = itemService.findAll();
        }
        log.info("GET -> api/v1/item with filter: {}", filter);
        return ResponseEntity.ok().body(BuildResponse.build(items));
    }
}
