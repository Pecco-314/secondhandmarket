package com.zerone.secondhandmarket.mapper;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ITEMCHECK;
import com.zerone.secondhandmarket.enums.ItemType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回Item对象
 * */
public class ItemRowMapper implements RowMapper<Item>{

    @Override
    public Item mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Item temp = new Item();
        temp.setItem_id(rs.getInt("item_id"));
        temp.setSeller_id(rs.getInt("seller_id"));
        temp.setItem_name(rs.getString("item_name"));
        temp.setType(ItemType.valueOf(rs.getString("item_type")));
        temp.setQuantity(rs.getInt("quantity"));
        temp.setPrice_now(rs.getDouble("price_now"));
        temp.setPrice_original(rs.getDouble("price_original"));
        temp.setKeyword(rs.getString("keyword"));
        temp.setIntroduction(rs.getString("introduction"));
        temp.setItem_pic_path(rs.getString("item_pic_path"));
        temp.setChecked(ITEMCHECK.valueOf(rs.getString("checked")));
        return temp;
    }
}

