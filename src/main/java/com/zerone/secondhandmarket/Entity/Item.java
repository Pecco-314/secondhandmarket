package com.zerone.secondhandmarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lzl
 * @since 2021-06-30
 */

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class Item {
    /**
     * 物品ID
     */
    private String itemID;
    /**
     * 卖家ID
     */
    private String sellerID;
    /**
     * 物品名
     */
    private String itemName;
    /**
     * 类型
     */
    private String type;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private Double price;
    /**
     * 关键字
     */
    private String key;
    /**
     * 简介
     */
    private String intro;
    /**
     * 是否通过审核
     */
    private Boolean checked;

}
