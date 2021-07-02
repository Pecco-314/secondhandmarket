package com.zerone.secondhandmarket.Controller.Visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerone.secondhandmarket.Message.AdminLoginMessage;
import com.zerone.secondhandmarket.Message.RegisterMessage;
import com.zerone.secondhandmarket.Message.UserLoginMessage;
import com.zerone.secondhandmarket.Module.LoginModule;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller("VisitorLogin")
public class LoginController {
    private LoginModule loginModule=new LoginModule();
    private ObjectMapper mapper=new ObjectMapper();
    @Autowired
    private UserService userService=new UserService();
    @Autowired
    private AdminService adminService=new AdminService();

    @ResponseBody
    @PostMapping ("/login/user")
    public String userLogin(@RequestBody UserLoginMessage data){
        String account= data.getEmailOrID();
        String password=data.getPassword();

        ResultVo result = loginModule.userLogin(userService,account,password);
        try {
            String json=mapper.writeValueAsString(result);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    @ResponseBody
    @PostMapping("/login/admin")
    public String adminLogin(@RequestBody AdminLoginMessage data){
        String account= data.getId();
        String password=data.getPassword();

        ResultVo resultVo=loginModule.adminLogin(adminService,account,password);

        try {
            String json=mapper.writeValueAsString(resultVo);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    @ResponseBody
    @PostMapping("/register")
    public String userRegister(@RequestBody RegisterMessage data) {
        String email = data.getEmail();
        String nickname = data.getNickName();
        String password = data.getPassword();

        ResultVo resultVo=loginModule.userRegister(userService,email,nickname,password);

        try {
            String json=mapper.writeValueAsString(resultVo);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
