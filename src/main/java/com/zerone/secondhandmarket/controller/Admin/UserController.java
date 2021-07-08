package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.message.UserModificationByAdministratorMessage;
import com.zerone.secondhandmarket.module.UserModule;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("AdminUser")
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    //获取所有用户列表
    @ResponseBody
    @PostMapping("requests/admin/users")
    public String getUserList() {
        Result result = UserModule.getUserList(userService);

        return result.toString();
    }

    //添加用户
    @ResponseBody
    @PostMapping("requests/admin/addUser")
    public String addUser(@RequestBody User user) {
        Result result = UserModule.insertNewUser(userService, user);

        return result.toString();
    }

    //删除用户
    @PostMapping("/requests/admin/deleteUser")
    @ResponseBody
    public String deleteUser(@RequestBody int userId) {
        Result result = UserModule.deleteUser(userService, userId);

        return result.toString();
    }

    //更新的时候只更新昵称
    @PostMapping("/requests/admin/modifyUser")
    @ResponseBody
    public String modifyUser(@RequestBody UserModificationByAdministratorMessage userModificationByAdministratorMessage) {
        //获取id对应的当前用户信息
        User user = userService.getUserById(userModificationByAdministratorMessage.getUserID());
        //设置用户的昵称信息
        user.setNickname(userModificationByAdministratorMessage.getNickName());

        Result result = UserModule.updateUserInfo(userService, user);

        return result.toString();
    }
}
