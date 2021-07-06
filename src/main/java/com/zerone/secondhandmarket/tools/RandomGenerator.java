package com.zerone.secondhandmarket.tools;

import java.security.SecureRandom;

public class RandomGenerator {
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomString(int count) {
        StringBuilder builder = new StringBuilder(count);

        for(int i = 0; i < count; ++i) {
            builder.append((char) ('a' + random.nextInt(26)));
        }

        return builder.toString();
    }
}
