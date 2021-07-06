package com.zerone.secondhandmarket.enums;

public enum ItemType {
    BOOK("书籍"),
    DAILY_USE("日常用品"),
    TICKET("票券"),
    DIGITAL("数码产品"),
    CLOTHES("衣鞋帽伞"),
    TRANSPORT("代步工具"),
    SPORTS("体育健身"),
    ELECTRIC("家用电器"),
    OTHERS("其他");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
