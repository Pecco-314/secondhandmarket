package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.AdminTokenMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.Result;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor  //无参构造函数
public class LoginModule {
    public static Result userLogin(UserService userService, String account, String password) {
        boolean isEmail = account.contains("@");

        User user = isEmail ? userService.getUserByEmail(account) : userService.getUserById(Integer.parseInt(account));

        if (user == null)
            return new Result(Status.NOT_FOUND, isEmail ? "找不到邮箱" : "找不到账号", null);
        else if (!CodeProcessor.validatePassword(password, user.getPassword()))
            return new Result(Status.PASSWORD_WRONG, "密码错误", null);
        else {
            Date date = new Date();
            return new Result(Status.OK, "登陆成功", new UserTokenMessage(user.getId(), CodeProcessor.encode(user.getId() + "@" + date)));
        }
    }

    public static Result adminLogin(AdminService adminService, String adminName, String password) {
        Administrator administrator = adminService.getAdminById(Integer.parseInt(adminName));

        if (administrator == null)
            return new Result(Status.NOT_FOUND, "找不到账号", null);
        else if (!CodeProcessor.validatePassword(password, administrator.getPassword()))
            return new Result(Status.PASSWORD_WRONG, "密码错误", null);
        else {
            Date date = new Date();
            return new Result(Status.OK, "登陆成功", new AdminTokenMessage(administrator.getId(), CodeProcessor.encode(administrator.getId() + "@" + date)));
        }
    }

    public static Result userRegister(UserService userService, String email, String nickname, String password) {
        if (userService.getUserByEmail(email) != null)
            return new Result(Status.HAS_REGISTERED, "邮箱已被注册", null);
        else {
            User user = new User();

            user.setEmailAddress(email);
            user.setNickname(nickname);
            user.setPassword(CodeProcessor.encode(password));

            userService.insertUser(user);
            return new Result(Status.OK, "注册成功", user);
        }
    }

    //获取管理员信息
    public static Result getAdminInfoFromId(AdminService adminService, int id) {
        Administrator admin = adminService.getAdminById(id);

        Result result = new Result(Status.OK, "获取成功", admin);
        return result;
    }
}
