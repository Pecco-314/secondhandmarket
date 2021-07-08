package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.SimplifiedUser;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.enums.Status;

import java.util.List;

public class UserModule {
    public static Result getUserList(UserService service) {
        List<User> list = service.getUserList();
        //List<SimplifiedUser> list = service.getSimplifiedUserList();

        if(list == null || list.isEmpty())
            return new Result(Status.USER_ERROR, "用户为空", null);

        return new Result(Status.USER_OK, "获取成功", list);
    }

    public static Result getUserInfo(UserService service, int userId) {
        User user = service.getUserById(userId);

        if(user == null)
            return new Result(Status.USER_ERROR, "获取失败", null);

        return new Result(Status.USER_OK, "获取成功", user);
    }

    public static Result getSimplifiedUserInfo(UserService service, int userId) {
        SimplifiedUser user = service.getSimplifiedUserInfoById(userId);

        if(user == null)
            return new Result(Status.USER_ERROR, "获取失败", null);

        return new Result(Status.USER_OK, "获取成功", user);
    }

    public static Result insertNewUser(UserService service, User user) {
        try {
            service.insertUser(user);

            return new Result(Status.USER_OK, "插入成功", null);
        } catch (Exception e) {
            return new Result(Status.USER_ERROR, "插入失败", null);
        }
    }

    public static Result updateUserInfo(UserService userService, User user) {
        try {
            userService.updateUser(user);

            return new Result(Status.USER_OK, "更新成功", null);
        } catch (Exception e) {
            return new Result(Status.USER_ERROR, "更新失败", null);
        }
    }

    public static Result deleteUser(UserService userService, int userId) {
        try {
            userService.deleteUser(userId);
            return new Result(Status.USER_OK, "删除成功", null);
        } catch (Exception e) {
            return new Result(Status.USER_ERROR, "删除失败", null);
        }
    }
}
