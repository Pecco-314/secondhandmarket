package com.zerone.secondhandmarket.Controller.Function;

import com.zerone.secondhandmarket.Controller.EntityForController.SimplelifiedUserInfo;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.ViewObject.Status;
import com.zerone.secondhandmarket.entity.Administrator;
import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginModule {
    @Autowired
    private static UserService userService;

    public static ResultVo userLogin(String emailAddress, String password){
        User user;
        if(emailAddress.contains("@"))
            user=userService.getUserByEmail(emailAddress);
        else
            user=userService.getUserById(Integer.parseInt(emailAddress));
        if(user==null){
            //若找到用户
            return new ResultVo(Status.ERROR,"邮箱不存在",null);
        }
        if(!user.getPassword().equals(password)){
            return new ResultVo(Status.ERROR,"密码错误",null);
        }

        return new ResultVo(Status.OK,"登陆成功",new SimplelifiedUserInfo(user.getUser_id(),user.getUsername(),user.getHead()));
    }

    public static ResultVo adminLogin(String adminname,String password){
        Administrator admin=null;
        return null;
    }

    public static int userRegister(User user){
        if(userService.insertUser(user)!=0){

            return 1;
        }
        else{
            return 0;
        }
    }
}
