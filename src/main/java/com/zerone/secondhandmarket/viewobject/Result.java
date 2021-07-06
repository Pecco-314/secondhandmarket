package com.zerone.secondhandmarket.viewobject;


import com.zerone.secondhandmarket.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data               //生成get()、set()
@NoArgsConstructor
public class Result {
    private Integer status;
    private String message;
    private Object data;

    public Result(Status status, String message, Object data) {
        this.status = status.getCode();
        this.message = message;
        this.data = data;
    }
}
