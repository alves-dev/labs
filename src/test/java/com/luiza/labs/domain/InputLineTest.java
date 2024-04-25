package com.luiza.labs.domain;

import com.luiza.labs.domain.exception.InputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;


class InputLineTest {

    @Test
    void shouldParseDataSuccessfully() throws InputException {
        String input = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308\n";
        Item item = InputLine.parser(input);

        Assertions.assertEquals(70L, item.getUserId());
        Assertions.assertEquals("Palmer Prosacco", item.getUserName());
        Assertions.assertEquals(753L, item.getOrderId());
        Assertions.assertEquals(3L, item.getProductId());
        Assertions.assertEquals(BigDecimal.valueOf(1836.74), item.getProductValue());
        Assertions.assertEquals(LocalDate.parse("2021-03-08"), item.getOrderDate());
    }

    @Test
    void shouldThrowExceptionDuringParsing(){
        String input = "0000000070                               Palmer Prosacco00000007530000000003     1836.7420210308\n";
        Assertions.assertThrows(InputException.class, () -> InputLine.parser(input));
    }

}
