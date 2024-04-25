package com.luiza.labs.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class HashUtilTest {

    @Test
    void testCalculateHash() throws NoSuchAlgorithmException {
        String text1 = "Hello World!";
        String text2 = "Hello World!";
        String text3 = "hello World!";

        String hash1 = HashUtil.calculateHash(text1.getBytes());
        String hash2 = HashUtil.calculateHash(text2.getBytes());
        String hash3 = HashUtil.calculateHash(text3.getBytes());

        Assertions.assertEquals(hash1, hash2);
        Assertions.assertNotEquals(hash1, hash3);
    }

}
