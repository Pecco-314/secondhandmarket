package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Order order = new Order();
        order.setId(rs.getInt("order_id"));
        order.setItem(rs.getInt("item_id"));
        order.setSeller(rs.getInt("seller_id"));
        order.setBuyer(rs.getInt("buyer_id"));
        order.setQuantity(rs.getInt("quantity"));
        order.setTime(rs.getString("ordering_time"));
        return order;
    }
}
