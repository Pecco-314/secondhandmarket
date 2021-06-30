package com.zerone.secondhandmarket.entity;

public enum ItemType {
    BOOK("书籍"),
    DAILYUSE("日常用品"),
    TICKET("票券"),
    OTHERS("其他");

    private String stateInfo;
    ItemType(String stateInfo)
    {

        this.stateInfo=stateInfo;
    }
    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }
}
