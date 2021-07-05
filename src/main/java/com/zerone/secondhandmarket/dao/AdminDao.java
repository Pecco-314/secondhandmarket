package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;

import java.util.List;

public interface AdminDao {
    // 用于添加管理员
    int insertAdmin(Administrator admin);

    // 用于删除管理员
    int deleteAdmin(int adminId);

    // 用于更新管理员
    int updateAdmin(Administrator admin);

    // 通过id查询管理员
    Administrator getAdminById(int adminId);
    // 通过用户名查询用户
    Administrator getAdminByNickname(String adminNickname);
    // 用于查询所有管理员列表
    List<Administrator> getAdminList();
}
