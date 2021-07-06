package com.zerone.secondhandmarket.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PathGenerator {
    private static final int itemRandomBits = 3;

    public static String generateItemImagePath() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return String.format("%s%s", format.format(new Date()), RandomGenerator.generateRandomString(itemRandomBits));
    }
}
