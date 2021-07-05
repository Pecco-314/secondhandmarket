package com.zerone.secondhandmarket.enums;

public enum ItemType {
    BOOK("书籍"),
    DAILYUSE("日常用品"),
    TICKET("票券"),
    DIGITAL("数码产品"),
    CLOTHES("衣鞋帽伞"),
    TRANSPORT("代步工具"),
    SPORT("体育健身"),
    ELECTRICAL("家用电器"),
    OTHERS("其他");

    private String stateInfo;

    ItemType(String stateInfo) {

        this.stateInfo = stateInfo;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
