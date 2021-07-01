package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.entity.UserHead;
import com.zerone.secondhandmarket.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public int insertUser(User user) {
        String sql = "insert into user(Nickname,user_id,phone_number,email,password,head_portrait) " +
                "values(:name,:id,:phonenum,:email,:password,:head_portrait)";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getUsername());
        param.put("id", user.getUser_id());
        param.put("phonenum", user.getPhone_number());
        param.put("email", user.getEmail());
        param.put("password", user.getPassword());
        param.put("head_portrait", user.getHead().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }

    public int deleteUser(int id) {
        String sql = "delete from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        jdbcTemplate.update(sql, param);
        return 0;
    }

    public int updateUser(User user) {
        String sql = "update user set Nickname=:name,phone_number=:phone_number,email=:email,password=:password,head_portrait=:head_portrait  where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getUsername());
        param.put("id", user.getUser_id());
        param.put("phone_number", user.getPhone_number());
        param.put("email", user.getEmail());
        param.put("password", user.getPassword());
        param.put("head_portrait", user.getHead().toString());
        jdbcTemplate.update(sql, param);
        return 0;
    }


    public User getUserById(int id) {
        User user = new User();
        String sql = "select * from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            user = jdbcTemplate.queryForObject(sql.toString(), param, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = new User();
        String sql = "select * from user where email=:email";
        Map<String, Object> param = new HashMap<>();
        param.put("email", email);
        try {
            user = jdbcTemplate.queryForObject(sql.toString(), param, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
        return user;
    }

    public List<User> getUserList() {
        String sql = "select * from user";
        List<User> users;
        try {
            users = jdbcTemplate.query(sql, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
        return users;
    }


}
