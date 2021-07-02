package com.zerone.secondhandmarket.Controller.EntityForController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class IdentFilter {
    private Integer seller;
    private Integer buyer;
}
