package com.zerone.secondhandmarket.controller.Visitor;

import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("VisitorItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();

//    @ResponseBody
//    @GetMapping("/product/filter")
//    public ResultVo getItemList(@RequestBody ItemFilter itemFilter){
//        return null;
//    }
//    @ResponseBody
//    @GetMapping("/product/id")
//    public ResultVo getItemInfo(@RequestBody int productId){
//        return null;
//    }

//    public String getItemList(@RequestBody ItemFilter filter) {
//        Result result = ItemModule.getItemList(itemService, filter);
//
//        return JSONMapper.writeValueAsString(result);
//    }
//
//    public String getItemInfo(@RequestBody int itemId) {
//        Result result = ItemModule.getItemInfo(itemService, itemId);
//
//        return JSONMapper.writeValueAsString(result);
//    }
}
