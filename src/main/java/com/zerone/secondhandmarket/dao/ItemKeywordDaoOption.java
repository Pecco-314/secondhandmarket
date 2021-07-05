package com.zerone.secondhandmarket.dao;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.KeywordsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemKeywordDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    // 用于添加商品关键词
    public int insertKeyword(int item_id,String keyword){
        String sql = "insert into keywords(keyword, item_id)" +
                "values(:keyword, :item_id)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id",item_id);
        param.put("keyword",keyword);
        return jdbcTemplate.update(sql, param);

    }


    // 用于删除商品关键词
    public  int deleteItem(int itemId,String keyword) {
        String sql = "delete from keywords where item_id=:id and keyword=:keyword";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        param.put("keyword",keyword);
        jdbcTemplate.update(sql, param);
        return 0;
    }
    // 通过id查询关键词
    public List<String> getKeywordsByItemId(int itemId) {
        String sql = "select * from keywords where item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", itemId);
        List<String> keywords;
        try {
            keywords = jdbcTemplate.query(sql, param, new KeywordsRowMapper());
        } catch (Exception e) {
            return null;
        }
        return keywords;
    }
}
