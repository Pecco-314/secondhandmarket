package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.entity.User;

import java.util.List;

public interface CartDao {
    // 用于添加购物车信息
    int insertCart(Cart cart);
    //插入或更新用户
    int insertOrUpdateCart(Cart cart);
    // 用于删除购物车信息
    int deleteCart(int userId,int itemId);
    //用于某个用户的清空购物车
    int clearCart(int userId);

    // 用于更新购物车数量信息
    int modifyItemQuantity(Cart cart);
    //查询用户购物车信息
    List<Cart> getCartListByUserId(int userId, Integer page);

    Integer getCartCount(int userId);
}
