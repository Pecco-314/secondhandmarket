package com.zerone.secondhandmarket.controller.Visitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerone.secondhandmarket.message.AdminLoginMessage;
import com.zerone.secondhandmarket.message.RegisterMessage;
import com.zerone.secondhandmarket.message.UserLoginMessage;
import com.zerone.secondhandmarket.module.LoginModule;
import com.zerone.secondhandmarket.viewobject.ResultVo;
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
    private LoginModule loginModule = new LoginModule();
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private UserService userService = new UserService();
    @Autowired
    private AdminService adminService = new AdminService();

    @RequestMapping("/login")
    public String userLogin() {
        return "login";
    }

    @ResponseBody
    @PostMapping("/login/user")
    public String userLogin(@RequestBody UserLoginMessage data) {
        String account = data.getEmailOrID();
        String password = data.getPassword();

        ResultVo result = loginModule.userLogin(userService, account, password);
        try {
            String json = mapper.writeValueAsString(result);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    @ResponseBody
    @PostMapping("/login/admin")
    public String adminLogin(@RequestBody AdminLoginMessage data) {
        String account = data.getId();
        String password = data.getPassword();

        ResultVo resultVo = loginModule.adminLogin(adminService, account, password);

        try {
            String json = mapper.writeValueAsString(resultVo);
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
        String nickname = data.getNickname();
        String password = data.getPassword();

        System.out.println(nickname);
        ResultVo resultVo = loginModule.userRegister(userService, email, nickname, password);

        try {
            String json = mapper.writeValueAsString(resultVo);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}
