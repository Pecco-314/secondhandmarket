package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.message.UserModificationByUserMessage;
import com.zerone.secondhandmarket.module.UserModule;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryUser")
public class UserController {
    @Autowired
    private UserService userService=new UserService();

    @GetMapping("user/{userId}")
    @ResponseBody
    public Result getUserInfo(@PathVariable int userId){
        Result result= UserModule.getUserInfo(userService,userId);

        return result;
    }
    @GetMapping("user/update")
    @ResponseBody
    public Result updateUserInfo(@RequestBody UserModificationByUserMessage userModificationByUserMessage){
        User user=new User();
        //根据更改信息设置用户的信息
        user.setId(userModificationByUserMessage.getUserID());
        user.setPassword(userModificationByUserMessage.getPassword());
        user.setEmailAddress(userModificationByUserMessage.getEmailAddress());
        user.setNickname(userModificationByUserMessage.getNickName());
        user.setImagePath(userModificationByUserMessage.getLocation());

        Result result=UserModule.updateUserInfo(userService,user);
        return result;
    }
}
