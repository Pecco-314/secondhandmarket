package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public int insertUser(User user) {
        String sql = "insert into user(name,user_id,phone_number,email,password) " +
                "values(:name,:id,:phonenum,:email,:password)";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getUsername());
        param.put("id",user.getUser_id());
        param.put("phonenum",user.getPhone_number());
        param.put("email",user.getEmail());
        param.put("password",user.getPassword());
        jdbcTemplate.update(sql, param);
        return 0;
    }

    public int deleteUser(int id) {
        String sql = "delete from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id",id);
        jdbcTemplate.update(sql,param);
        return 0;
    }
    public int updateUser(User user) {
        String sql = "update user set name=:name  where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getUsername());
        param.put("id",user.getUser_id());
        jdbcTemplate.update(sql,param);
        return 0;
    }


    public User getUser(int id) {
        User user = new User();
        String sql = "select * from user where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            user = jdbcTemplate.queryForObject(sql.toString(), param, (ResultSet rs, int rowNum) -> {
                User temp = new User();
                temp.setUser_id(rs.getInt("user_id"));
                temp.setUsername(rs.getString("name"));
                return temp;
            });
        } catch (Exception e) {
            return null;
        }
        //使用一次性的RowMapper

        //        新建MyRowMapper类实现RowMapper接口，重写mapRow方法，指定返回User对象
        // User user = jdbcTemplate.queryForObject(sql,param,new UserRowMapper());
        return user;
    }
}
