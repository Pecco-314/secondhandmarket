package com.zerone.secondhandmarket.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Order {
    private int order_id;
    private int buyer_id;
    private int seller_id;
    private int item_id;//商品id
    private int quantity;//数量
    private Date ordering_time;//下单时间

    public int getItem_id() {
        return item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public Date getOrdering_time() {
        return ordering_time;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setOrdering_time(Date ordering_time) {
        this.ordering_time = ordering_time;
    }

}
