package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.module.UploadModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.PathGenerator;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ItemService itemService=new ItemService();
    @Autowired
    private TagsService tagsService=new TagsService();
    @Autowired
    private ItemImageService itemImageService=new ItemImageService();

    @RequestMapping("/post")
    public String openPostPage() {
        return "post";
    }

    @ResponseBody
    @PostMapping("/requests/upload/image")
    public String upload(@RequestParam("multipartfiles") MultipartFile[] multipartfiles) throws IOException {
        Result result= UploadModule.upload("item",multipartfiles);
        System.out.println(JSONMapper.writeValueAsString(result));
        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/post")
    public String postItem(@RequestBody SellingItemMessage sellingItemMessage){
        Result result;
        //检验id与token是否一致
        if(CodeProcessor.validateIdToken(sellingItemMessage.getSeller()+"",sellingItemMessage.getToken())) {
            result=  ItemModule.releaseUserItem(itemService,tagsService,itemImageService,sellingItemMessage);
        } else{
            result=new Result(Status.RELEASE_ITEM_ERROR,"发布失败，id与token不一致",null);
        }

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/product/filter")
    public String getItemListByFilter(@RequestBody ItemFilter itemFilter){
        Result result=ItemModule.getItemsByFilter(itemService,itemImageService,tagsService,itemFilter);

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/item/{itemId}")
    public String getItemInfoByItemId(@PathVariable("itemId") int itemId) {
        Result result=ItemModule.getItemInfo(itemService,itemImageService,tagsService,itemId);

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/search")
    public String getItemInfo(@RequestBody String keyword) {
        Result result=ItemModule.getItemsByKeyWords(itemService,itemImageService,tagsService,keyword);

        return result.toString();
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
