package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.mapper.TagsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemTagsDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    // 用于添加商品关键词
    public int insertTag(int itemId,String tag){
        String sql = "insert into keywords(keyword, item_id)" +
                "values(:keyword, :item_id)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id",itemId);
        param.put("keyword",tag);
        return jdbcTemplate.update(sql, param);
    }


    // 用于删除商品关键词
    public  int deleteItem(int itemId,String keyword) {
        String sql = "delete from keywords where item_id=:item_id and keyword=:keyword";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        param.put("keyword",keyword);
        return jdbcTemplate.update(sql, param);
    }
    // 通过id查询关键词
    public List<String> getTagsByItemId(int itemId) {
        String sql = "select * from keywords where item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        try {
            return jdbcTemplate.query(sql, param, new TagsRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
}
