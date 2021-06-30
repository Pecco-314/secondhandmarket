package com.zerone.secondhandmarket.entity;

public enum UserHead {
    HEAD1("图片路径**"),
    HEAD2("图片路径**"),
    HEAD0("图片路径**");

    private String picPath;
    UserHead(String stateInfo)
    {

        this.picPath =stateInfo;
    }
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
