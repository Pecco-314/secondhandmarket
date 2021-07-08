package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.message.AdminTokenMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor  //无参构造函数
public class LoginModule {

    public static Result userLogin(UserService userService, String account, String password) {
        User user;
        if (account.contains("@")) {
            //判断为邮箱登录
            user = userService.getUserByEmail(account);
        } else {
            //判断为ID登录
            user = userService.getUserById(Integer.parseInt(account));
        }

        if (user == null) {
            //若未找到用户
            if (account.contains("@")) {
                return new Result(Status.NOT_FOUND, "找不到邮箱", new UserTokenMessage());
            } else {
                return new Result(Status.NOT_FOUND, "找不到账号", new UserTokenMessage());
            }
        } else {
            if (!CodeProcessor.validatePassword(password,user.getPassword())) {
                return new Result(Status.PASSWORD_WRONG, "密码错误", new UserTokenMessage());
            } else {
                //获取登录时间
                Date date = new Date();
                return new Result(Status.OK, "登陆成功", new UserTokenMessage(user.getId(), CodeProcessor.encode(user.getId() + "@" + date)));
            }
        }
    }

    public static Result adminLogin(AdminService adminService, String adminName, String password) {
        Administrator administrator = adminService.getAdminById(Integer.parseInt(adminName));

        Result result;
        if (administrator == null) {
            result = new Result(Status.NOT_FOUND, "找不到账号", new AdminTokenMessage());
        } else {
            if (!CodeProcessor.validatePassword(password,administrator.getPassword())) {
                result = new Result(Status.PASSWORD_WRONG, "密码错误", new AdminTokenMessage());
            } else {
                Date date = new Date();
                result = new Result(Status.OK, "登陆成功", new AdminTokenMessage(administrator.getId(), CodeProcessor.encode(administrator.getId() + "@" + date)));
            }
        }
        return result;
    }

    public static Result userRegister(UserService userService, String email, String nickname, String password) {
        Result result;
        User user = new User();

        if (userService.getUserByEmail(email) != null)
            result = new Result(Status.HAS_REGISTERED, "邮箱已被注册", user);
        else {
            user.setEmailAddress(email);
            user.setNickname(nickname);
            user.setPassword(CodeProcessor.encode(password));
            userService.insertUser(user);
            result = new Result(Status.OK, "注册成功", user);
        }
        return result;
    }
}
