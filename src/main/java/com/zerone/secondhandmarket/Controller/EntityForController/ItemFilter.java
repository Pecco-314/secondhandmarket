package com.zerone.secondhandmarket.Controller.EntityForController;

import com.zerone.secondhandmarket.entity.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class ItemFilter {
    private Integer userId;
    private ItemType type;
    private String key;
    private Ordering priceOrdering;
    private Ordering quantityOrdering;
}
