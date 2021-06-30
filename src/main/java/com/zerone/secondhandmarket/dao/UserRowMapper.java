package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.User;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int value) throws SQLException {
        User user = new User ();
        user.setUser_id(new Integer(rs.getInt("id")));
        user.setUsername(rs.getString("username"));
        return user;
    }

}
