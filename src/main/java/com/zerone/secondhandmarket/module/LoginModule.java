package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.message.TokenMessage;
import com.zerone.secondhandmarket.viewobject.ResultVo;
import com.zerone.secondhandmarket.viewobject.Status;
import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import lombok.NoArgsConstructor;

@NoArgsConstructor  //无参构造函数
public class LoginModule {

    public ResultVo userLogin(UserService userService,String account, String password){
        User user;
        if(account.contains("@")) {
            //判断为邮箱登录
            user = userService.getUserByEmail(account);
        }
        else {
            //判断为ID登录
            user = userService.getUserById(Integer.parseInt(account));
        }

        if(user==null){
            //若未找到用户
            if(account.contains("@")){
                return new ResultVo(Status.NOTFOUND,"找不到邮箱",new TokenMessage());
            }
            else{
                return  new ResultVo(Status.NOTFOUND,"找不到账号",new TokenMessage());
            }
        }
        else {
            if (!user.getPassword().equals(password)) {
                return new ResultVo(Status.PASSWORDWRONG, "密码错误", new TokenMessage());
            }
            else{
                return new ResultVo(Status.OK, "登陆成功", new TokenMessage(user.getUser_id(), ""));
         }
        }
    }

    public ResultVo adminLogin(AdminService adminService, String adminName, String password){
        Administrator administrator=adminService.getAdminById(Integer.parseInt(adminName));

        ResultVo resultVo;
        if(administrator==null){
            resultVo= new ResultVo(Status.NOTFOUND,"找不到账号",new TokenMessage());
        }
        else {
            if (!administrator.getPassword().equals(password)) {
                resultVo = new ResultVo(Status.PASSWORDWRONG, "密码错误", new TokenMessage());
            } else
                resultVo = new ResultVo(Status.OK, "登陆成功", new TokenMessage(administrator.getAdmin_id(),""));

        }
        return resultVo;
    }

    public ResultVo userRegister(UserService userService, String email,String nickname,String password){
        ResultVo resultVo;
        User user = new User();

        if (userService.getUserByEmail(email) != null)
            resultVo = new ResultVo(Status.HASREGISTERED, "邮箱已被注册", user);
        else {
            user.setEmail(email);
            user.setUsername(nickname);
            user.setPassword(password);
            //暂时加一个电话
            user.setPhone_number("312412");
            userService.insertUser(user);
            resultVo = new ResultVo(Status.OK, "注册成功", user);
        }
        return resultVo;
    }
}
