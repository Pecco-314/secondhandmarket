package com.zerone.secondhandmarket.Message;

import com.zerone.secondhandmarket.entity.ItemType;
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
    private String keyWords;
    private String introduction;
    private byte[] image;
}
