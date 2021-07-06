package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ImageDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    // 用于添加购物车信息
    public int insertImage(String imagePath) {
        String sql = "insert into images(imagePath) " +
                "values(:imagePath)";
        Map<String, Object> param = new HashMap<>();
        param.put("imagePath", imagePath);
        return  jdbcTemplate.update(sql, param);
    }

    // 用于删除购物车信息
    public int deleteImage(String imagePath) {
        String sql = "delete from images where imagePath=:imagePath";
        Map<String, Object> param = new HashMap<>();
        param.put("imagePath", imagePath);
        jdbcTemplate.update(sql, param);
        return 0;
    }

}
