package com.zerone.secondhandmarket.ViewObject;


public class ResultVo{
    private Status status;
    private String message;
    private Object data;

    public ResultVo(Status sta, String str, Object obj){
        status=sta;
        message=str;
        data=obj;
    }
}
