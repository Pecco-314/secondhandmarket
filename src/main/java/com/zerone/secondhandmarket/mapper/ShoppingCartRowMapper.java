package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.ShoppingCart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCartRowMapper implements RowMapper<ShoppingCart> {
    @Override
    public ShoppingCart mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        ShoppingCart temp = new ShoppingCart();
        temp.setUserID(rs.getInt("user_id"));
        temp.setItemID(rs.getInt("item_id"));
        temp.setQuantity(rs.getInt("quantity"));
        return temp;
    }
}
