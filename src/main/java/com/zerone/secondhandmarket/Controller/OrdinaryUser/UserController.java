package com.zerone.secondhandmarket.Controller.OrdinaryUser;

import com.zerone.secondhandmarket.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryUser")
public class UserController {
    @GetMapping("user/{userid}")
    @ResponseBody
    public User getUserInfo(@PathVariable int userid){
        return null;
    }
    @GetMapping("user/update")
    @ResponseBody
    public User updateUserInfo(@RequestBody User user){
        return null;
    }
}
