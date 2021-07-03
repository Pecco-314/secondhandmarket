package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.ItemType;

import java.util.List;

public interface OrderDao {
    // 用于添加订单信息
    int insertOrder(Order order);

    // 用于删除商品
    int deleteOrder(int orderId);

    // 用于更新商品
    int updateOrder(Order order);
    // 通过itemid查询订单信息
    Order getOrderByOrderId(int orderId);
    // 通过关键字查询订单信息
    List<Order> getOrderByKeyword(String keyword);
    // 通过userid查询订单信息
    List<Order> getOrderByUserId(int userId);
}
