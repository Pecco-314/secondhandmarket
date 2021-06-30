package com.zerone.secondhandmarket.dao;
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

    // 用于查询用户
    User getUser(int userId);

    // 用于查询用户列表
    /*List<User> getUserList(@Param("userCondition") User userCondition,
                           @Param("rowIndex") int rowIndex,
                           @Param("pageSize") int pageSize);

    // 用于查询用户列表数量
    int getUserCount(@Param("userCondition") User userCondition);*/

}

