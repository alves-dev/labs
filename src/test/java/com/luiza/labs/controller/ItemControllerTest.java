package com.luiza.labs.controller;

import com.luiza.labs.controller.response.UserResponse;
import com.luiza.labs.domain.Item;
import com.luiza.labs.domain.exception.FilterException;
import com.luiza.labs.service.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    private List<Item> mockAllItems;

    @BeforeEach
    public void setUp() {
        Item item1 = new Item(1L, "userName", 1L, 2L, BigDecimal.ONE, LocalDate.now());
        Item item2 = new Item(2L, "userName2", 2L, 2L, BigDecimal.ONE, LocalDate.now());

        mockAllItems = new ArrayList<>();
        mockAllItems.add(item1);
        mockAllItems.add(item2);
    }

    @Test
    void listItemsNoFilter() throws FilterException {
        when(itemService.findAll()).thenReturn(mockAllItems);

        ResponseEntity<List<UserResponse>> responseEntity = itemController.list(null, null, null);

        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void listItemsFilterOrderId() throws FilterException {
        Item item2 = new Item(2L, "userName2", 2L, 2L, BigDecimal.ONE, LocalDate.now());
        List<Item> mockOrderId = new ArrayList<>();
        mockOrderId.add(item2);
        when(itemService.findByOrderId(2L)).thenReturn(mockOrderId);

        ResponseEntity<List<UserResponse>> responseEntity = itemController.list(2L, null, null);

        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Assertions.assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).size());
        Assertions.assertEquals("userName2", Objects.requireNonNull(responseEntity.getBody()).getFirst().name());
    }

    @Test
    void listItemsFilterDate() throws FilterException {
        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now();
        when(itemService.findByOrderDateBetween(start, end)).thenReturn(mockAllItems);

        ResponseEntity<List<UserResponse>> responseEntity = itemController.list(null, start, end);

        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Assertions.assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    void listItemsFilterException() {
        FilterException exception;

        exception = Assertions.assertThrows(FilterException.class, () ->
                itemController.list(-10L, null, null));
        Assertions.assertTrue(exception.getMessage().contains("orderId filter value is invalid"));

        exception = Assertions.assertThrows(FilterException.class, () ->
                itemController.list(null, LocalDate.now(), null));
        Assertions.assertTrue(exception.getMessage().contains("To use the date filter, both dates must be entered"));

        exception = Assertions.assertThrows(FilterException.class, () ->
                itemController.list(null, LocalDate.now().plusDays(1), LocalDate.now()));
        Assertions.assertTrue(exception.getMessage().contains("start filter value is invalid"));
    }
}
