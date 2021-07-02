package com.zerone.secondhandmarket.mapper;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.entity.UserHead;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回User对象
 * */
public class UserRowMapper implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        User temp = new User();
        temp.setUser_id(rs.getInt("user_id"));
        temp.setUsername(rs.getString("Nickname"));
        temp.setPhone_number(rs.getString("phone_number"));
        temp.setEmail(rs.getString("email"));
        temp.setPassword(rs.getString("password"));
        temp.setHead(UserHead.valueOf(rs.getString("head_portrait")));
        return temp;
    }
}

