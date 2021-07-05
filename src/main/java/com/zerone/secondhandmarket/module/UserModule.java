package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.ResultVo;
import com.zerone.secondhandmarket.viewobject.Status;

public class UserModule {
    public ResultVo getUserInfo(UserService userService, int userid) {
        User user = userService.getUserById(userid);
        return new ResultVo(Status.OK, "获取成功", user);
    }

    public ResultVo updateUserInfo(UserService userService, User user) {
        userService.updateUser(user);
        return new ResultVo(Status.OK, "更新成功", null);
    }

    public ResultVo deleteUserInfo(UserService userService, int userid) {
        userService.deleteUser(userid);
        return new ResultVo(Status.OK, "删除成功", null);
    }
}
