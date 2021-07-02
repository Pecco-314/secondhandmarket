package com.zerone.secondhandmarket.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class TokenMessage {
    //locate
    private Integer userID;

    //update
    private String token;
}
