package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.message.AdminTokenMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.ResultVo;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor  //无参构造函数
public class LoginModule {

    public ResultVo userLogin(UserService userService, String account, String password) {
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
                return new ResultVo(Status.NOT_FOUND, "找不到邮箱", new UserTokenMessage());
            } else {
                return new ResultVo(Status.NOT_FOUND, "找不到账号", new UserTokenMessage());
            }
        } else {
            if (!user.getPassword().equals(CodeProcessor.encoded(password))) {
                return new ResultVo(Status.PASSWORD_WRONG, "密码错误", new UserTokenMessage());
            } else {
                //获取登录时间
                Date date=new Date();
                return new ResultVo(Status.OK, "登陆成功", new UserTokenMessage(user.getUser_id(), CodeProcessor.encoded(user.getUser_id() + "@"+date)));
            }
        }
    }

    public ResultVo adminLogin(AdminService adminService, String adminName, String password) {
        Administrator administrator = adminService.getAdminById(Integer.parseInt(adminName));

        ResultVo resultVo;
        if (administrator == null) {
            resultVo = new ResultVo(Status.NOT_FOUND, "找不到账号", new AdminTokenMessage());
        } else {
            if (!administrator.getPassword().equals(CodeProcessor.encoded(password))) {
                resultVo = new ResultVo(Status.PASSWORD_WRONG, "密码错误", new AdminTokenMessage());
            } else {
                Date date=new Date();
                resultVo = new ResultVo(Status.OK, "登陆成功", new AdminTokenMessage(administrator.getAdmin_id(), CodeProcessor.encoded(administrator.getAdmin_id() + "@" + date)));
            }
        }
        return resultVo;
    }

    public ResultVo userRegister(UserService userService, String email, String nickname, String password) {
        ResultVo resultVo;
        User user = new User();

        if (userService.getUserByEmail(email) != null)
            resultVo = new ResultVo(Status.HAS_REGISTERED, "邮箱已被注册", user);
        else {
            user.setEmail(email);
            user.setUsername(nickname);
            user.setPassword(CodeProcessor.encoded(password));
            userService.insertUser(user);
            resultVo = new ResultVo(Status.OK, "注册成功", user);
        }
        return resultVo;
    }
}
