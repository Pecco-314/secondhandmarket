package com.zerone.secondhandmarket.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//修改购物车的物品数量时发送此信息

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
//update
public class CartModificationMessage {
    //locate
    private Integer userID;
    private Integer itemID;

    //modify
    private Integer quantity;
}
