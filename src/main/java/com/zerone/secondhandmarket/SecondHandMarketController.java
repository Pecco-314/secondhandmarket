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
        User user=new User(6,"huhu","123456","1111111","111@qq.com", UserHead.HEAD1);
        Administrator admin=new Administrator(2,"jxy","1111156");
       // adminService.insertAdmin(admin);
       // userService.insertUser(user);
        //adminService.deleteAdmin(1);
        //userService.deleteUser(1);
        //System.out.println(userService.getUserById(3).toString());
        //System.out.println(adminService.getAdminById(2).toString());
        //System.out.println(userService.getUserByEmail("111@qq.com").toString());
        List<User> users=userService.getUserList();
        for(User user1:users)
            System.out.println(user1);
        //String str=userService.getUserByEmail("123@qq.com").toString();
        //System.out.println(str);
        //userService.insertUser(user);
       //userService.updateUser(user);
        //adminService.updateAdmin(admin);
        return "index";
    }

    // TODO: 下面这些也许应该放到一个统一的类里？
    @RequestMapping("/post")
    public String openPostPage(){
        return "post";
    }

    @RequestMapping("/login")
    public String openLoginPage(){
        return "login";
    }
}
