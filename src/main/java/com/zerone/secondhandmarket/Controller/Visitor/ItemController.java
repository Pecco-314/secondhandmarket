package com.zerone.secondhandmarket.Controller.Visitor;

import com.zerone.secondhandmarket.Controller.EntityForController.ItemFilter;
import com.zerone.secondhandmarket.ViewObject.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("VisitorItem")
public class ItemController {
    @ResponseBody
    @GetMapping("/product/filter")
    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
        return null;
    }
    @ResponseBody
    @GetMapping("/product/{productId}")
    public ResultVo getItemInfo(@PathVariable("productId") int productId){
        return null;
    }
}
