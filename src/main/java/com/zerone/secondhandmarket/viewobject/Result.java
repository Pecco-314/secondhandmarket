package com.zerone.secondhandmarket.viewobject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerone.secondhandmarket.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data               //生成get()、set()
@NoArgsConstructor
public class Result {
    private static ObjectMapper mapper = new ObjectMapper();

    private Integer status;
    private String message;
    private Object data;

    public Result(Status status, String message, Object data) {
        this.status = status.getCode();
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
