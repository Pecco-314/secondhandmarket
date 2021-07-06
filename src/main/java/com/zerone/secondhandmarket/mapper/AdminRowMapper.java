package com.zerone.secondhandmarket.mapper;
import com.zerone.secondhandmarket.entity.Administrator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回Admin对象
 * */
public class AdminRowMapper implements RowMapper<Administrator>{

    @Override
    public Administrator mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        Administrator admin = new Administrator();
        admin.setId(rs.getInt("id"));
        admin.setNickname(rs.getString("admin_name"));
        admin.setPassword(rs.getString("password"));
        return admin;
    }
}

