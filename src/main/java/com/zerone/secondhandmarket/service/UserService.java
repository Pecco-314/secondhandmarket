package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.UserDaoOption;
import com.zerone.secondhandmarket.dao.UserDao;
import com.zerone.secondhandmarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class UserService implements UserDao {
    @Autowired
    private UserDaoOption daooption;

    @Override
    public int insertUser(User user) {
        daooption.insertUser(user);
        return 0;
    }

    @Override
    public int deleteUser(int id) {
        daooption.deleteUser(id);
        return 0;
    }

    @Override
    public int updateUser(User user) {
        daooption.updateUser(user);
        return 0;
    }

    @Override
    public User getUserById(int id) {
        return daooption.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return daooption.getUserByEmail(email);
    }
    @Override
    public List<User> getUserList(){return daooption.getUserList();}
}
