package com.zerone.secondhandmarket.entity;

public class ShoppingCart {
    private int user_id;
    private int item_id;
    private int quantity;
    public ShoppingCart()
    {

    }
    public ShoppingCart(int user_id,int item_id,int quantity)
    {
        this.user_id=user_id;
        this.item_id=item_id;
        this.quantity=quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getItem_id() {
        return item_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
