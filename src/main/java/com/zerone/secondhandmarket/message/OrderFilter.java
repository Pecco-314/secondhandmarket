package com.zerone.secondhandmarket.message;

import com.zerone.secondhandmarket.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
//select
public class OrderFilter {
    private Integer buyer = null;
    private Integer seller = null;
    private Integer Item = null;
    private OrderState state = null;
}
