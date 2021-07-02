package com.zerone.secondhandmarket.ViewObject;

public enum Status {
    OK(10200),NOTFOUND(10400),PASSWORDWRONG(10401),HASREGISTERED(10402);
    private  Integer code;

    Status(Integer code){
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
