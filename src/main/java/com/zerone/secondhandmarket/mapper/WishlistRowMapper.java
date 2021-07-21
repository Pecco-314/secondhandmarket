package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.entity.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRowMapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Wishlist temp = new Wishlist();
        temp.setUserId(rs.getInt("user_id"));
        temp.setItemId(rs.getInt("item_id"));
        return temp;
    }
}
