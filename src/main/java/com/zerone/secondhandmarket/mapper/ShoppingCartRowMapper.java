package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Cart;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingCartRowMapper implements RowMapper<Cart> {
    @Override
    public Cart mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Cart temp = new Cart();
        temp.setUserId(rs.getInt("user_id"));
        temp.setItemId(rs.getInt("item_id"));
        temp.setQuantity(rs.getInt("quantity"));
        return temp;
    }
}
