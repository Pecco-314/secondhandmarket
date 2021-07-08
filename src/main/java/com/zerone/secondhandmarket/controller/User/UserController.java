package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.PasswordModificationMessage;
import com.zerone.secondhandmarket.message.UserModificationByUserMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.module.UserModule;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("OrdinaryUser")
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    @PostMapping("/requests/user/info")
    @ResponseBody
    public String getUserInfo(@RequestBody UserTokenMessage userIdToken) {
        Result result;
        if(CodeProcessor.validateIdToken(userIdToken.getUserID()+"",userIdToken.getToken()))
            result = UserModule.getUserInfo(userService, userIdToken.getUserID());
        else
            result = new Result(Status.USER_ERROR,"id与token不一致",null);
        return result.toString();
    }

    //更新个人信息
    @PostMapping("/requests/user/update")
    @ResponseBody
    public String updateUserInfo(@RequestBody UserModificationByUserMessage userModificationByUserMessage) {
        Result result;
        if(CodeProcessor.validateIdToken(userModificationByUserMessage.getUserID()+"",userModificationByUserMessage.getToken())){
            User user = userService.getUserById(userModificationByUserMessage.getUserID());

            //根据更改信息设置用户的信息
            user.setPhoneNumber(userModificationByUserMessage.getTelephone());
            user.setEmailAddress(userModificationByUserMessage.getEmailAddress());
            user.setNickname(userModificationByUserMessage.getNickName());

            result = UserModule.updateUserInfo(userService, user);
        }

        else
            result = new Result(Status.USER_ERROR,"id与token不一致",null);

        return result.toString();
    }

    //更新个人密码
    @PostMapping("/requests/user/updatePassword")
    @ResponseBody
    public String updatePassword(@RequestBody PasswordModificationMessage passwordModificationMessage) {
        Result result;

        if(CodeProcessor.validateIdToken(passwordModificationMessage.getUserID()+"",passwordModificationMessage.getToken())){
            User user=userService.getUserById(passwordModificationMessage.getUserID());
            //根据信息修改密码
            if(CodeProcessor.validatePassword(passwordModificationMessage.getOldPassword(),user.getPassword()))
            {
                user.setPassword(CodeProcessor.encode(passwordModificationMessage.getNewPassword()));
                result=UserModule.updateUserInfo(userService,user);
            }else{
                result=new Result(Status.USER_ERROR,"旧密码输入错误",null);
            }
        }
        else
            result = new Result(Status.USER_ERROR,"id与token不一致",null);

        return result.toString();
    }


}
