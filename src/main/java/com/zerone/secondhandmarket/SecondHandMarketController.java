package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.entity.UserHead;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SecondHandMarketController {
    @Autowired
    UserService userService=new UserService();
    @Autowired
    AdminService adminService=new AdminService();
    @RequestMapping("/")
    public String run() {
        User user=new User(6,"huahua","123456","1111111","123@qq.com", UserHead.HEAD1);
        Administrator admin=new Administrator(1,"pecco","123456");
      //  adminService.insertAdmin(admin);
        List<User> users=userService.getUserList();
        for(User user1:users)
            System.out.println(user1);
        //String str=userService.getUserByEmail("123@qq.com").toString();
        //System.out.println(str);
        //userService.insertUser(user);
        return "index";
    }

}
