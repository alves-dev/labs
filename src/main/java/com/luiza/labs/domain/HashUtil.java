package com.luiza.labs.domain;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe útil para gerar hash, usando o algoritmo SHA-256.
 */
public class HashUtil {

    private HashUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String calculateHash(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        return calculateHash(file.getBytes());
    }

    public static String calculateHash(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(bytes);

        StringBuilder hexString = new StringBuilder();
        for (byte hashByte : hashBytes) {
            String hex = Integer.toHexString(0xff & hashByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
