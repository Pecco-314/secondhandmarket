package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.entity.UserHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecondHandMarketController {
    @Autowired
    UserService userService=new UserService();
    @RequestMapping("/")
    public String run() {
      //  User user=new User(1,"pecco","123456","1111111","123@qq.com", UserHead.HEAD0);
        //userService.insertUser(user);
        return "index";
    }

}
