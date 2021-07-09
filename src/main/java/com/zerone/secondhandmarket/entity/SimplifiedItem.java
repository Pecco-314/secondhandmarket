package com.zerone.secondhandmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class SimplifiedItem {
    private Integer id;//物品的id
    private String name;//物品名称
    private Double price;//现在的价格
    private String[] tags;//关键词
    private String imagePath;//封面图的路径
}
