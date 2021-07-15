package com.zerone.secondhandmarket.controller.Visitor;

import com.zerone.secondhandmarket.message.AdminLoginMessage;
import com.zerone.secondhandmarket.message.RegisterMessage;
import com.zerone.secondhandmarket.message.UserLoginMessage;
import com.zerone.secondhandmarket.module.LoginModule;
import com.zerone.secondhandmarket.service.AdminService;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("VisitorLogin")
public class LoginController {
    @Autowired
    private UserService userService = new UserService();
    @Autowired
    private AdminService adminService = new AdminService();

    @RequestMapping("/login")
    public String userLogin() {
        return "login";
    }
    @RequestMapping("/log-in")
    public String userLog_in() {
        return "log-in";
    }
    @ResponseBody
    @PostMapping("/requests/login/user")
    public String userLogin(@RequestBody UserLoginMessage data) {
        String account = data.getEmailOrID();
        String password = data.getPassword();

        Result result = LoginModule.userLogin(userService, account, password);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/login/admin")
    public String adminLogin(@RequestBody AdminLoginMessage data) {
        String account = data.getId();
        String password = data.getPassword();

        Result result = LoginModule.adminLogin(adminService, account, password);

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/admin/info/{id}")
    public String getAdminInfo(@PathVariable int id) {
        Result result = LoginModule.getAdminInfoFromId(adminService, id);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/register")
    public String userRegister(@RequestBody RegisterMessage data) {
        String email = data.getEmail();
        String nickname = data.getNickname();
        String password = data.getPassword();

        System.out.println(nickname);
        Result result = LoginModule.userRegister(userService, email, nickname, password);

        return result.toString();
    }
}
