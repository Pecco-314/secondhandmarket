package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.message.UserModificationByAdministratorMessage;
import com.zerone.secondhandmarket.module.UserModule;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("AdminUser")
public class UserController {
    private UserService userService = new UserService();

    public String getUserList() {
        Result result = UserModule.getUserList(userService);

        return JSONMapper.writeValueAsString(result);
    }

    @GetMapping("/requests/delete/{userId}")
    @ResponseBody
    public String deleteUser(@PathVariable int userId){
        Result result = UserModule.deleteUser(userService, userId);

        return JSONMapper.writeValueAsString(result);
    }

    @GetMapping("/requests/modify")
    @ResponseBody
    public String modifyUser(@RequestBody UserModificationByAdministratorMessage userModificationByAdministratorMessage){
        //获取id对应的当前用户信息
        User user = userService.getUserById(userModificationByAdministratorMessage.getUserID());
        //设置更新后的用户信息
        user.setNickname(userModificationByAdministratorMessage.getNickName());
        Result result= UserModule.updateUserInfo(userService,user);

        return JSONMapper.writeValueAsString(result);
    }
}
