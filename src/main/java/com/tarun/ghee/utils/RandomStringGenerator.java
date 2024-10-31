package com.tarun.ghee.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;


public class RandomStringGenerator {
    private static final String ALPHANUMERIC_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALPHANUMERIC_CHARACTERS.length());
            stringBuilder.append(ALPHANUMERIC_CHARACTERS.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }
}