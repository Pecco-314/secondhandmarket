package com.zerone.secondhandmarket.Controller.Visitor;

import com.zerone.secondhandmarket.Controller.EntityForController.Login;
import com.zerone.secondhandmarket.Controller.Function.LoginModule;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller("VisitorLogin")
public class LoginController {
    @ResponseBody
    @PostMapping("http://localhost:8088/login")
    public ResultVo userLogin(@RequestBody Login login){
        String account= login.getEmail();
        String password=login.getPassword();

        ResultVo result = LoginModule.userLogin(account,password);
        return result;
    }

    @ResponseBody
    @PostMapping("/admin/login")
    public ResultVo adminLogin(@RequestBody Login login){
        String account= login.getEmail();
        String password=login.getPassword();

        ResultVo result = LoginModule.userLogin(account,password);
        return result;
    }

    @ResponseBody
    @PostMapping("/user/register")
    public int userRegister(@RequestBody User user){
        return LoginModule.userRegister(user);
    }
}
