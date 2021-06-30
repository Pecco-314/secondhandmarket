package com.zerone.secondhandmarket.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class ShoppingCart {
    /**
     * 用户ID
     */
    private int userID;
    /**
     * 物品ID
     */
    private int itemID;
    /**
     * 数量
     */
    private int quantity;
}
