package com.zerone.secondhandmarket.entity;

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
    public Item()
    {

    }
    public Item(int item_id,int seller_id,String item_name,ItemType type,int quantity,double price,String keyword,String introduction,String item_path)
    {
        this.item_id=item_id;
        this.item_name=item_name;
        this.type=type;
        this.seller_id=seller_id;
        this.quantity=quantity;
        this.price=price;
        this.introduction=introduction;
        this.keyword=keyword;
        this.item_pic_path=item_path;
    }

    public String getItem_pic_path() {
        return item_pic_path;
    }

    public void setItem_pic_path(String item_pic_path) {
        this.item_pic_path = item_pic_path;
    }

    public int getItem_id() {
        return item_id;
    }

    public double getPrice() {
        return price;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public ItemType getType() {
        return type;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
