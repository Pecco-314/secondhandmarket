package com.zerone.secondhandmarket.Controller.Function;

import com.zerone.secondhandmarket.Controller.EntityForController.ItemFilter;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import com.zerone.secondhandmarket.entity.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public class ItemModule {
    public static ResultVo getItemList(ItemFilter itemFilter){
        return null;
    }

    public static ResultVo getItemInfo(int productId){
        return null;
    }

    public ResultVo addUserItem(Item item){
        return null;
    }

    public ResultVo modifyUserItem(Item item){
        return null;
    }

    public ResultVo deleteUserItem(Item item){
        return null;
    }
}
