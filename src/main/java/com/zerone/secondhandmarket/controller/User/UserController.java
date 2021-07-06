package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryUser")
public class UserController {
    @GetMapping("user/{userId}")
    @ResponseBody
    public User getUserInfo(@PathVariable int userId){
        System.out.println(userId);
        return null;
    }
//    @GetMapping("user/update")
//    @ResponseBody
//    public User updateUserInfo(@RequestBody User user){
//        return null;
//    }
}
