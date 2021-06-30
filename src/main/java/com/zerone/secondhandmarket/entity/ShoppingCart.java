package com.zerone.secondhandmarket.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class ShoppingCart {
    private int user_id;
    private int item_id;
    private int quantity;

}
