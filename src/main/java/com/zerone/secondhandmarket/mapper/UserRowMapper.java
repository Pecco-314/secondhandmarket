package com.zerone.secondhandmarket.mapper;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.enums.UserHead;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现RowMapper接口，返回User对象
 */
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {
//        获取结果集中的数据
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setNickname(rs.getString("Nickname"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setEmailAddress(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setHead(UserHead.valueOf(rs.getString("head_portrait")));
        return user;
    }
}

