package com.zerone.secondhandmarket.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class Item {
    private int item_id;//物品的id
    private int seller_id;//卖家的id
    private String item_name;//物品名称
    private ItemType type;//物品类型
    private int quantity;//数量
    private double price;//价格
    private String keyword;//关键词
    private String introduction;//简介
    private String item_pic_path;//上传的商品的图片的路径，可能有多张图片存在一个文件夹里，即文件夹路径
}
