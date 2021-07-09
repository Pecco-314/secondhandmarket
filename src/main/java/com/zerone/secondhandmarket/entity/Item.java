package com.zerone.secondhandmarket.entity;

import com.zerone.secondhandmarket.enums.ItemCheckCondition;
import com.zerone.secondhandmarket.enums.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lzl
 * @since 2021-06-30
 */

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class Item {
    private int id;//物品的id
    private int seller;//卖家的id
    private String name;//物品名称
    private ItemType type;//物品类型
    private int quantity;//数量
    private double price;//现在的价格
    private double originalPrice;//原来的价格
    private String introduction;//简介
    private String coverPath;
    private String releaseTime;//商品发布时间
    private List<String> itemTags;//返回关键词数组
    private List<String> itemImages;//返回物品图片路径数组

    /**
     * 是否通过审核
     */
    private ItemCheckCondition checkCondition=ItemCheckCondition.UNCHECKED;
}