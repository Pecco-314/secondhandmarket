package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.enums.Status;

public class UserModule {
    public static Result getUserInfo(UserService userService, int userid) {
        User user = userService.getUserById(userid);
        return new Result(Status.OK, "获取成功", user);
    }

    public static Result updateUserInfo(UserService userService, User user) {
        userService.updateUser(user);
        return new Result(Status.OK, "更新成功", null);
    }

    public static Result deleteUserInfo(UserService userService, int userid) {
        userService.deleteUser(userid);
        return new Result(Status.OK, "删除成功", null);
    }
}
