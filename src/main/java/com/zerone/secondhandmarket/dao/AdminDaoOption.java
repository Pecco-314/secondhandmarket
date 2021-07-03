package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.mapper.AdminRowMapper;
import com.zerone.secondhandmarket.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class AdminDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    public int insertAdmin(Administrator admin) {
        String sql = "insert into administrator(admin_name, password) " +
                "values(:admin_name,:password)";
        Map<String, Object> param = new HashMap<>();
        param.put("admin_name", admin.getAdmin_name());
        param.put("id",admin.getAdmin_id());
        param.put("password",admin.getPassword());
        jdbcTemplate.update(sql, param);
        return 0;
    }

    public int deleteAdmin(int id) {
        String sql = "delete from administrator where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id",id);
        jdbcTemplate.update(sql,param);
        return 0;
    }
    public int updateAdmin(Administrator admin) {
        String sql = "update administrator set admin_name=:name,password=:password  where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("name", admin.getAdmin_name());
        param.put("id",admin.getAdmin_id());
        param.put("password",admin.getPassword());
        jdbcTemplate.update(sql,param);
        return 0;
    }


    public Administrator getAdminById(int id) {
        Administrator admin=new Administrator();
        String sql = "select * from administrator where id=:id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        try {
            admin = jdbcTemplate.queryForObject(sql.toString(), param, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
        return admin;
    }
    public Administrator getAdminByName(String name) {
        Administrator admin=new Administrator();
        String sql = "select * from administrator where name=:name";
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        try {
            admin = jdbcTemplate.queryForObject(sql.toString(), param, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
        return admin;
    }
    public List<Administrator> getAdminList() {
        String sql = "select * from administrator";
        List<Administrator> admins;
        try {
            admins = jdbcTemplate.query(sql, new AdminRowMapper());
        } catch (Exception e) {
            return null;
        }
        return admins;
    }


}
