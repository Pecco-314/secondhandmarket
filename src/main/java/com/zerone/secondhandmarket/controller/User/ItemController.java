package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.tools.PathGenerator;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller("OrdinaryItem")
public class ItemController {
    @RequestMapping("/post")
    public String openPostPage() {

        return "post";
    }

    @ResponseBody
    @PostMapping("/requests/upload/image")
    public String upload(@RequestParam("multipartfiles") MultipartFile[] multipartfiles) throws IOException {
        //获取项目编译之后的文件路径，一般是“项目路径/target/classes”
        String Path = (String.valueOf(Thread.currentThread().getContextClassLoader().getResource("") + "static/swagger/")).replaceAll("file:/", "").replaceAll("%20", " ").trim();
        if (Path.indexOf(":") != 1) {
            Path = File.separator + Path;
        }
        //遍历文件
        if (multipartfiles != null && multipartfiles.length > 0) {
            for (MultipartFile item : multipartfiles) {
                System.out.println(item.getOriginalFilename());
                String type = item.getOriginalFilename().split("\\.")[1];//获取文件类型
                String fileName = PathGenerator.generateItemImagePath() + "." + type;
                String filePath = Path + "uploadFiles/uploadFiles";//自定义上传路径
                File file = new File(filePath, fileName);
                if (!file.exists()) {//判断文件夹是否存在，如果不存在则创建
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                item.transferTo(file);//上传文件
            }
        }
        return "post";
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
