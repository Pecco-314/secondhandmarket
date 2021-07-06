package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Administrator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemImageRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        String imagePath = rs.getString("imagePath");
        return imagePath;
    }
}

