package com.zerone.secondhandmarket.viewobject;


import com.zerone.secondhandmarket.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data               //生成get()、set()
@NoArgsConstructor
public class ResultVo {
    private Integer status;
    private String message;
    private Object data;

    public ResultVo(Status sta, String str, Object obj) {
        status = sta.getCode();
        message = str;
        data = obj;
    }
//    @Override
//    public String toString(){
//        return String.format("{\"status\":\"%s\",\"message\":\"%s\",%s}",status,message,data.toString());
//    }
}
