package com.zerone.secondhandmarket.message;

import com.zerone.secondhandmarket.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class SellingItemMessage {
    private Integer seller;
    private String name;
    private ItemType type;
    private Integer quantity;
    private Double originalPrice;
    private Double price;
    private String keyWords;
    private String introduction;
    private byte[] image;
}
