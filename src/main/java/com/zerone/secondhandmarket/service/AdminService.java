package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.AdminDao;
import com.zerone.secondhandmarket.dao.AdminDaoOption;
import com.zerone.secondhandmarket.dao.UserDaoOption;
import com.zerone.secondhandmarket.dao.UserDao;
import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService implements AdminDao {
    @Autowired
    private AdminDaoOption daooption;

    @Override
    public int insertAdmin(Administrator admin) {
        daooption.insertAdmin(admin);
        return 0;
    }

    @Override
    public int deleteAdmin(int id) {
        daooption.deleteAdmin(id);
        return 0;
    }

    @Override
    public int updateAdmin(Administrator admin) {
        daooption.updateAdmin(admin);
        return 0;
    }

    @Override
    public Administrator getAdminById(int id) {
        return daooption.getAdminById(id);
    }

    @Override
    public Administrator getAdminByNickname(String name) {
        return daooption.getAdminByName(name);
    }

    @Override
    public List<Administrator> getAdminList() {
        return daooption.getAdminList();
    }
}
