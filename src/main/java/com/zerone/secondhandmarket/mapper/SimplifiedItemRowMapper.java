package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.SimplifiedItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimplifiedItemRowMapper implements RowMapper<SimplifiedItem> {

    @Override
    public SimplifiedItem mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        SimplifiedItem temp = new SimplifiedItem();
        temp.setId(rs.getInt("item_id"));
        temp.setName(rs.getString("item_name"));
        temp.setPrice(rs.getDouble("price_now"));
        temp.setImagePath(rs.getString("item_pic_path"));
        return temp;
    }
}

