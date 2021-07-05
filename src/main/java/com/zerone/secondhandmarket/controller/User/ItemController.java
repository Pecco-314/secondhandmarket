package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("OrdinaryItem")
public class ItemController {
    @RequestMapping("/request/post")
    public String openPostPage(){
        return "post";
    }
//    @GetMapping("/product/filter")
//    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
//        return null;
//    }
//
    @GetMapping("/request/item/{itemId}")
    public Result getItemInfo(@PathVariable("itemId") int itemId){
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
