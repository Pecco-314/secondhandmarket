package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.enums.ITEMCHECK;
import com.zerone.secondhandmarket.enums.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimplifiedItemRowMapper implements RowMapper<SimplifiedItem> {

    @Override
    public SimplifiedItem mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        SimplifiedItem temp = new SimplifiedItem();
        temp.setItem_id(rs.getInt("item_id"));
        temp.setItem_name(rs.getString("item_name"));
        temp.setPrice_now(rs.getDouble("price_now"));
        temp.setItem_pic_path(rs.getString("item_pic_path"));
        return temp;
    }
}

