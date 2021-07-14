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
            return updateUser(user);
        else
            return insertUser(user);
    }

    public int insertUser(User user) {
        String sql = "insert into user(Nickname,phone_number,email,password,imagePath) " +
                "values(:name,:phonenum,:email,:password,:imagePath)";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getNickname());
        param.put("phonenum", user.getPhoneNumber());
        param.put("email", user.getEmailAddress());
        param.put("password", user.getPassword());
        param.put("imagePath", user.getImagePath());
        return jdbcTemplate.update(sql, param);
    }

    public int deleteUser(int id) {
        String sql = "delete from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return jdbcTemplate.update(sql, param);
    }

    public int updateUser(User user) {
        String sql = "update user set Nickname=:name,phone_number=:phone_number,email=:email,password=:password,imagePath=:imagePath  where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("name", user.getNickname());
        param.put("id", user.getId());
        param.put("phone_number", user.getPhoneNumber());
        param.put("email", user.getEmailAddress());
        param.put("password", user.getPassword());
        param.put("imagePath", user.getImagePath());
        return jdbcTemplate.update(sql, param);
    }


    public User getUserById(int id) {
        String sql = "select * from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public User getUserByEmail(String emailAddress) {
        String sql = "select * from user where email=:email";
        Map<String, Object> param = new HashMap<>();
        param.put("email", emailAddress);
        try {
            return jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getUserList() {
        String sql = "select * from user";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
    //查询用户简略信息
   public SimplifiedUser getSimplifiedUserInfoById(int userId)
    {
        String sql = "select user_id,Nickname,imagePath from user where user_id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", userId);
        try {
            return jdbcTemplate.queryForObject(sql, param, new SimplifiedUserRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

}