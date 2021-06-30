package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.dao.DaoOption;
import com.zerone.secondhandmarket.dao.UserDao;
import com.zerone.secondhandmarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDao {
    @Autowired
    private DaoOption daooption;

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
    public User getUser(int id) {
         return daooption.getUser(id);
    }
}
