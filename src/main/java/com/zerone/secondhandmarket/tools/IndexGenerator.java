package com.zerone.secondhandmarket.tools;

public class IndexGenerator {
    public static final int countPerPage = 12;

    public static int generateStartIndex(int page) {
        return (page - 1) * countPerPage;
    }
}
