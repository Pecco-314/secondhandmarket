package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.SimplifiedUser;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.mapper.SimplifiedUserRowMapper;
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

    public int insertOrUpdateUser(User user) {
        if (getUserById(user.getId()) != null)
            updateUser(user);
        else insertUser(user);
        return 0;
    }

    public int insertUser(User user) {
        String sql = "insert into user(Nickname,phone_number,email,password,head_portrait) " +
                "values(:name,:phonenum,:email,:password,:head_portrait)";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getNickname());
        param.put("phonenum", user.getPhoneNumber());
        param.put("email", user.getEmailAddress());
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
        param.put("name", user.getNickname());
        param.put("id", user.getId());
        param.put("phone_number", user.getPhoneNumber());
        param.put("email", user.getEmailAddress());
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

    public User getUserByEmail(String emailAddress) {
        User user = new User();
        String sql = "select * from user where email=:email";
        Map<String, Object> param = new HashMap<>();
        param.put("email", emailAddress);
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
    //查询用户简略信息
   public SimplifiedUser getSimplifiedUserInfoById(int userId)
    {
        SimplifiedUser simple_user;
        String sql = "select user_id,Nickname,head_portrait from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", userId);
        try {
            simple_user = jdbcTemplate.queryForObject(sql.toString(), param, new SimplifiedUserRowMapper());
        } catch (Exception e) {
            return null;
        }
        return simple_user;
    }

}