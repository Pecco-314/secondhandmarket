package com.zerone.secondhandmarket.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Cart {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 物品ID
     */
    private Integer itemId;
    /**
     * 数量
     */
    private Integer quantity;
}
