package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.OrderDao;
import com.zerone.secondhandmarket.dao.OrderDaoOption;
import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.message.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService implements OrderDao {
    @Autowired
    OrderDaoOption daoOption = new OrderDaoOption();

    @Override
    // 用于添加订单信息
    public int insertOrder(Order order) {
        return daoOption.insertOrder(order);
    }

    @Override
    // 用于删除商品
    public int deleteOrder(int orderId) {
        return daoOption.deleteOrder(orderId);
    }

    @Override
    // 用于更新商品
    public int updateOrder(Order order) {
        return daoOption.updateOrder(order);
    }

    @Override
    // 通过itemId查询订单信息
    public Order getOrderByOrderId(int orderId) {
        return daoOption.getOrderByOrderId(orderId);
    }

    @Override
    // 通过关键字查询订单信息
    public List<Order> getOrderByKeyword(String keyword) {
        return daoOption.getOrderByKeyword(keyword);
    }

    @Override
    // 通过userid查询订单信息
    public List<Order> getOrderByUserId(int userId, boolean isBuyer) {
        return daoOption.getOrderByUserId(userId, isBuyer);
    }

    public List<Order> getOrderListByFilter(OrderFilter filter) {
        return daoOption.getOrderListByFilter(filter);
    }
}
