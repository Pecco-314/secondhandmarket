package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.ITEMCHECK;
import com.zerone.secondhandmarket.enums.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Order temp = new Order();
        temp.setOrder_id(rs.getInt("order_id"));
        temp.setItem_id(rs.getInt("item_id"));
        temp.setSeller_id(rs.getInt("seller_id"));
        temp.setBuyer_id(rs.getInt("buyer_id"));
        temp.setQuantity(rs.getInt("quantity"));
        temp.setOrdering_time(rs.getString("ordering_time"));
        return temp;
    }
}
