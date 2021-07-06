package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.UserDaoOption;
import com.zerone.secondhandmarket.dao.UserDao;
import com.zerone.secondhandmarket.entity.SimplifiedUser;
import com.zerone.secondhandmarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserService implements UserDao {
    @Autowired
    private UserDaoOption daoOption;

    @Override
    public int insertUser(User user) {
        daoOption.insertUser(user);
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        daoOption.deleteUser(id);
        return 0;
    }

    @Override
    public int updateUser(User user) {
        daoOption.updateUser(user);
        return 0;
    }

    @Override
    public User getUserById(int id) {
        return daoOption.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return daoOption.getUserByEmail(email);
    }

    @Override
    public List<User> getUserList() {
        return daoOption.getUserList();
    }

    @Override
    public int insertOrUpdateUser(User user) {
        return daoOption.insertOrUpdateUser(user);
    }

    @Override
    public SimplifiedUser getSimplifiedUserInfoById(int userId) {
        return daoOption.getSimplifiedUserInfoById(userId);
    }
}
