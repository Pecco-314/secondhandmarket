package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.module.UploadModule;

import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.PathGenerator;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static com.zerone.secondhandmarket.tools.JSONMapper.writeValueAsString;

@Controller("OrdinaryItem")
public class ItemController {

    @RequestMapping("/post")
    public String openPostPage() {

        return "post";
    }

    @ResponseBody
    @PostMapping("/requests/upload/image")
    public String upload(@RequestParam("multipartfiles") MultipartFile[] multipartfiles) throws IOException {
        Result result= UploadModule.upload("item",multipartfiles);
        System.out.println(JSONMapper.writeValueAsString(result));
        return JSONMapper.writeValueAsString(result);
    }

    //    @GetMapping("/product/filter")
//    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
//        return null;
//    }
//
    @GetMapping("/request/item/{itemId}")
    public Result getItemInfo(@PathVariable("itemId") int itemId) {
        return null;
    }
//
//    @GetMapping("/user/addItem")
//    public ResultVo addUserItem(@RequestBody Item item){
//        return null;
//    }
//
//    @GetMapping("/user/modifyItem")
//    public ResultVo modifyUserItem(@RequestBody Item item){
//        return null;
//    }
//
//    @GetMapping("/user/deleteItem")
//    public ResultVo deleteUserItem(@RequestBody Item item){
//        return null;
//    }
}
