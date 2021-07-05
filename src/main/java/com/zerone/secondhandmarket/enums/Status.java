package com.zerone.secondhandmarket.enums;

public enum Status {
    OK(10200),
    NOT_FOUND(10400),
    PASSWORD_WRONG(10401),
    HAS_REGISTERED(10402),
    ;

    private final Integer code;

    Status(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
