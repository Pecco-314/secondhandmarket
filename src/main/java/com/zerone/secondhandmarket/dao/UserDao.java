package com.zerone.secondhandmarket.dao;
import com.zerone.secondhandmarket.entity.SimplifiedUser;
import com.zerone.secondhandmarket.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    // 用于添加用户
    int insertUser(User user);

    // 用于删除用户
    int deleteUser(int userId);

    // 用于更新用户
    int updateUser(User user);
    //插入或更新用户
    int insertOrUpdateUser(User user);
    // 通过id查询用户
    User getUserById(int userId);
    // 通过邮箱查询用户
    User getUserByEmail(String emailAddress);
    // 用于查询所有用户列表
    List<User> getUserList();
    //查询用户简略信息
    SimplifiedUser getSimplifiedUserInfoById(int userId);
    // 用于查询用户列表数量
   // int getUserCount(@Param("userCondition") User userCondition);

}

