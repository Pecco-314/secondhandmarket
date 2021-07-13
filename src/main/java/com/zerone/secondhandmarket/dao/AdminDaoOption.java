package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.mapper.AdminRowMapper;
import com.zerone.secondhandmarket.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class AdminDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public int insertAdmin(Administrator admin) {
        String sql = "insert into administrator(admin_name, password) values(:admin_name,:password)";
        Map<String, Object> param = new HashMap<>();
        param.put("admin_name", admin.getNickname());
        param.put("id",admin.getId());
        param.put("password",admin.getPassword());
        return jdbcTemplate.update(sql, param);
    }

    public int deleteAdmin(int id) {
        String sql = "delete from administrator where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id",id);
        return jdbcTemplate.update(sql,param);
    }
    public int updateAdmin(Administrator admin) {
        String sql = "update administrator set admin_name=:name,password=:password where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("name", admin.getNickname());
        param.put("id",admin.getId());
        param.put("password",admin.getPassword());
        return jdbcTemplate.update(sql,param);
    }


    public Administrator getAdminById(int id) {
        String sql = "select * from administrator where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, param, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
    public Administrator getAdminByName(String name) {
        String sql = "select * from administrator where name=:name";
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        try {
            return jdbcTemplate.queryForObject(sql, param, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
    public List<Administrator> getAdminList() {
        String sql = "select * from administrator";
        try {
            return jdbcTemplate.query(sql, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
}
