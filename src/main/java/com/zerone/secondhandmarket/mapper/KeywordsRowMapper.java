package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.Administrator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeywordsRowMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        String temp=rs.getString("keyword");;
        return temp;
    }
}

