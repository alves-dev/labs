package com.luiza.labs.domain;

import com.luiza.labs.domain.exception.InputException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Este objeto representa uma linha do arquivo de entrada.
 */
public class InputLine {

    private InputLine() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parser de linha de entrada.
     *
     * @param line String referente a uma linda do arquivo
     * @return {@link Item}
     * @throws InputException
     */
    public static Item parser(String line) throws InputException {
        int userIdSize = 10;
        int nameSize = 45;
        int orderIdSize = 10;
        int productIdSize = 10;
        int productPriceSize = 12;
        int orderDateSize = 8;
        int totalSize = userIdSize + nameSize + orderIdSize + productIdSize + productPriceSize + orderDateSize;

        line = line.replace("\n", "");
        if (line.length() != totalSize) {
            throw new InputException("Invalid line size");
        }

        try {
            int index = 0;
            Long userId = Long.parseLong(line.substring(index, index + userIdSize));

            index += userIdSize;
            String name = line.substring(index, index + nameSize).trim();

            index += nameSize;
            Long orderId = Long.parseLong(line.substring(index, index + orderIdSize));

            index += orderIdSize;
            Long productId = Long.parseLong(line.substring(index, index + productIdSize));

            index += productIdSize;
            String value = line.substring(index, index + productPriceSize).trim();
            BigDecimal productPrice = new BigDecimal(value);

            index += productPriceSize;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate orderDate = LocalDate.parse(line.substring(index, index + orderDateSize), formatter);

            return new Item(userId, name, orderId, productId, productPrice, orderDate);
        } catch (Exception error) {
            throw new InputException("Invalid parse line: " + error.getCause());
        }
    }
}
