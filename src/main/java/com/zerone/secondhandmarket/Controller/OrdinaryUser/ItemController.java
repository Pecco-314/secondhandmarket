package com.zerone.secondhandmarket.Controller.OrdinaryUser;

import com.zerone.secondhandmarket.Controller.EntityForController.ItemFilter;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("OrdinaryItem")
public class ItemController {
    @GetMapping("/product/filter")
    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
        return null;
    }

    @GetMapping("/product/{productId}")
    public ResultVo getItemInfo(@PathVariable("productId") int productId){
        return null;
    }

    @GetMapping("/user/addItem")
    public ResultVo addUserItem(@RequestBody Item item){
        return null;
    }

    @GetMapping("/user/modifyItem")
    public ResultVo modifyUserItem(@RequestBody Item item){
        return null;
    }

    @GetMapping("/user/deleteItem")
    public ResultVo deleteUserItem(@RequestBody Item item){
        return null;
    }
}
