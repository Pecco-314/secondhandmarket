package com.zerone.secondhandmarket.enums;

public enum UserHead {
    HEAD1("图片路径**"),
    HEAD2("图片路径**"),
    HEAD0("图片路径**"),
    ;

    private final String imagePath;

    UserHead(String imagePath) {

        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
