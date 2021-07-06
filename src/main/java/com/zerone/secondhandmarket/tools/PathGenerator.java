package com.zerone.secondhandmarket.tools;

import com.zerone.secondhandmarket.entity.Item;

public class PathGenerator {
    private static final int itemRandomBits = 10;

    public static String generateItemImagePath(Item item) {
        return String.format("%s_%d_%s", RandomGenerator.generateRandomString(itemRandomBits), item.getId(), item.getName());
    }
}
