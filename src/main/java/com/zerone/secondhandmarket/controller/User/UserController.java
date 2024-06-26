package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.User;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.PasswordModificationMessage;
import com.zerone.secondhandmarket.message.UserHeadModificationMessage;
import com.zerone.secondhandmarket.message.UserModificationByUserMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.module.UploadModule;
import com.zerone.secondhandmarket.module.UserModule;
import com.zerone.secondhandmarket.service.UserService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.Router;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Controller("OrdinaryUser")
public class UserController {
    @Autowired
    private UserService userService = new UserService();

    @RequestMapping("/wishlist")
    public String openWishlistPage(HttpServletRequest request) {
        String res = Router.routerForUser(request, "wishlist");
        //System.out.println(res);
        return res;
    }

    @RequestMapping("/user")
    public String openUserPage(HttpServletRequest request) {
        String res = Router.routerForUser(request, "my-account");
        //System.out.println(res);
        return res;
    }

    @RequestMapping("/checkout")
    public String openCheckoutPage(HttpServletRequest request) {
        String res = Router.routerForUser(request, "checkout");
        //System.out.println(res);
        return res;
    }

    //获取用户信息
    @PostMapping("/requests/user/info")
    @ResponseBody
    public String getUserInfo(@RequestBody UserTokenMessage userIdToken) {
        Result result;
        if (CodeProcessor.validateIdToken(userIdToken.getUserID(), userIdToken.getToken()))
            result = UserModule.getUserInfo(userService, userIdToken.getUserID());
        else
            result = new Result(Status.TOKEN_MISMATCH, "id与token不一致", null);
        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/user/infos")
    public String getUsersByIds(@RequestBody List<Integer> ids) {
        Result result = UserModule.getUsersByIds(userService, ids);
        return result.toString();
    }

    //更新个人信息
    @PostMapping("/requests/user/info/update")
    @ResponseBody
    public String updateUserInfo(@RequestBody UserModificationByUserMessage userModificationByUserMessage) {
        Result result;
        if (CodeProcessor.validateIdToken(userModificationByUserMessage.getUserID(), userModificationByUserMessage.getToken())) {
            User user = userService.getUserById(userModificationByUserMessage.getUserID());

            if (user == null)
                return new Result(Status.USER_ERROR, "无法获取用户", null).toString();

            //根据更改信息设置用户的信息
            user.setPhoneNumber(userModificationByUserMessage.getTelephone());
            user.setEmailAddress(userModificationByUserMessage.getEmailAddress());
            user.setNickname(userModificationByUserMessage.getNickname());

            result = UserModule.updateUserInfo(userService, user);
        } else
            result = new Result(Status.TOKEN_MISMATCH, "id与token不一致", null);

        return result.toString();
    }

    //更新用户头像
    @PostMapping("/requests/user/head/update")
    @ResponseBody
    public String updateUserHead(@RequestBody UserHeadModificationMessage userHeadModificationMessage) {
        Result result;
        if (CodeProcessor.validateIdToken(userHeadModificationMessage.getUserID(), userHeadModificationMessage.getToken())) {
            User user = userService.getUserById(userHeadModificationMessage.getUserID());

            if (user == null)
                return new Result(Status.USER_ERROR, "无法获取用户", null).toString();

            //根据更改信息设置用户的信息
            user.setImagePath(userHeadModificationMessage.getImageUrl());

            result = UserModule.updateUserInfo(userService, user);
        } else
            result = new Result(Status.TOKEN_MISMATCH, "id与token不一致", null);

        return result.toString();
    }

    //更新个人密码
    @PostMapping("/requests/user/password/update")
    @ResponseBody
    public String updatePassword(@RequestBody PasswordModificationMessage passwordModificationMessage) {
        Result result;

        if (CodeProcessor.validateIdToken(passwordModificationMessage.getUserID(), passwordModificationMessage.getToken())) {
            User user = userService.getUserById(passwordModificationMessage.getUserID());
            if (user == null)
                return new Result(Status.USER_ERROR, "无法获取用户", null).toString();
            //根据信息修改密码
            if (CodeProcessor.validatePassword(passwordModificationMessage.getOldPassword(), user.getPassword())) {
                user.setPassword(CodeProcessor.encode(passwordModificationMessage.getNewPassword()));
                result = UserModule.updateUserInfo(userService, user);
            } else {
                result = new Result(Status.PASSWORD_ERROR, "旧密码输入错误", null);
            }
        } else
            result = new Result(Status.TOKEN_MISMATCH, "id与token不一致", null);

        return result.toString();
    }

    //上传头像
    @ResponseBody
    @PostMapping("/requests/user/upload/image")
    public String upload(@RequestParam("multipartFile") MultipartFile multipartFile) {
        try {
            MultipartFile[] multipartFiles = {multipartFile};
            Result result = UploadModule.upload("user", multipartFiles);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();

            return new Result(Status.ERROR, "文件传输失败", null).toString();
        }
    }

    //获取用户图片
    @ResponseBody
    @GetMapping("/requests/user/{imagePath}")
    public byte[] getImage(@PathVariable("imagePath") String imagePath) {
        String courseFile = null;
        try {
            courseFile = new File("").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        File file = new File((courseFile) + "/uploadFiles/user/" + imagePath);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
