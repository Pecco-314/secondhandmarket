package com.zerone.secondhandmarket.message;

import com.zerone.secondhandmarket.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//update
@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class SellingItemModificationMessage {
    //locate
    private Integer itemID;

    //update
    private String name;
    private ItemType type;
    private Integer quantity;
    private Double originalPrice;
    private Double price;
    private String[] tags;
    private String introduction;
    private String[] images;
}
