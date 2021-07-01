package com.zerone.secondhandmarket.Controller.Function;

import com.zerone.secondhandmarket.Controller.EntityForController.ShoppingCartInfo;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class ShoppingCartModule {
    public ResultVo getShoppingCartItem(int userId){
        return null;
    }
    public ResultVo ModifyItemCount(ShoppingCartInfo sh){
        return null;
    }
}
