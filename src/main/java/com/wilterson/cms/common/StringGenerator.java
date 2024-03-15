/*
 * Copyright 2024 Wilterson Franco
 */

package com.wilterson.cms.common;

import java.security.SecureRandom;

public record StringGenerator() {

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {

        if (length < 1 || length > 50) {
            throw new IllegalArgumentException("Length must be between 1 and 50");
        }

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
