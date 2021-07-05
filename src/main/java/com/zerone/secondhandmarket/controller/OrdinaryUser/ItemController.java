package com.zerone.secondhandmarket.controller.OrdinaryUser;

import com.zerone.secondhandmarket.viewobject.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("OrdinaryItem")
public class ItemController {
    @RequestMapping("/post")
    public String openPostPage(){
        return "post";
    }
//    @GetMapping("/product/filter")
//    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
//        return null;
//    }
//
    @GetMapping("/product/{productId}")
    public ResultVo getItemInfo(@PathVariable("productId") int productId){
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
