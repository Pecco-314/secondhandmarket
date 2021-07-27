package com.zerone.secondhandmarket.tools;

public class IndexGenerator {
    public static final int countPerPage = 6;
    public static final int countPerPageInShop = 12;

    public static int generateStartIndex(int page, boolean inShop) {
        return (page - 1) * (inShop ? countPerPageInShop : countPerPage);
    }
}
