package com.zerone.secondhandmarket.Controller.OrdinaryUser;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zerone.secondhandmarket.Controller.EntityForController.ShoppingCartInfo;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.entity.ShoppingCart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryShoppingCart")
public class ShoppingCartController {
    @ResponseBody
    @GetMapping("/user/{userId}")
    public ResultVo getShoppingCartItem(@PathVariable("userId") int userId){
        return null;
    }
    @ResponseBody
    @GetMapping("/user/modify")
    public ResultVo ModifyItemCount(@RequestBody ShoppingCartInfo sh){
        return null;
    }
}
