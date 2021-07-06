package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.mapper.ItemImageRowMapper;
import com.zerone.secondhandmarket.mapper.TagsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemImageDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    // 用于添加商品图片
    public int insertItemImage(int itemId, String imagePath){
        String sql = "insert into item_image(imagePath, itemId) VALUES " +
                "(:imagePath,:itemId)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id",itemId);
        param.put("imagePath",imagePath);
        return jdbcTemplate.update(sql, param);

    }


    // 用于删除商品图片
    public  int deleteItemImage(int itemId, String imagePath){
        String sql = "delete from item_image where itemId=:item_id and imagePath=:imagePath";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        param.put("imagePath",imagePath);
        jdbcTemplate.update(sql, param);
        return 0;
    }
    // 通过id查询图片
    public List<String> getImagesByItemId(int itemId) {
        String sql = "select * from item_image where itemId=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        List<String> images;
        try {
            images = jdbcTemplate.query(sql, param, new ItemImageRowMapper());
        } catch (Exception e) {
            return null;
        }
        return images;
    }
}
