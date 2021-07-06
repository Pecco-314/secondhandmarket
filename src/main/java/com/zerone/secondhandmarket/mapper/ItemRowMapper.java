package com.zerone.secondhandmarket.mapper;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemCheckCondition;
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
        Item item = new Item();
        item.setId(rs.getInt("item_id"));
        item.setSeller(rs.getInt("seller_id"));
        item.setName(rs.getString("item_name"));
        item.setType(ItemType.valueOf(rs.getString("item_type")));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getDouble("price_now"));
        item.setOriginalPrice(rs.getDouble("price_original"));
     //   temp.setKeyword(rs.getString("keyword"));
        item.setIntroduction(rs.getString("introduction"));
        item.setCoverPath(rs.getString("coverPath"));
        item.setCheckCondition(ItemCheckCondition.valueOf(rs.getString("checked")));
        return item;
    }
}

