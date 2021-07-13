package com.zerone.secondhandmarket.controller.Visitor;

import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("VisitorItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();
    @Autowired
    private ItemImageService itemImageService = new ItemImageService();
    @Autowired
    private TagsService tagsService = new TagsService();

    @RequestMapping("/shop")
    public String userLogin() {
        return "shop";
    }

    @ResponseBody
    @GetMapping("/index/items")
    public String getIndexItems() {
        Result result = ItemModule.getIndexItemList(itemService, itemImageService, tagsService);
        System.out.println(result);
        return result.toString();
    }
    

    @ResponseBody
    @GetMapping("/shop/items")
    public String getAllItems() {
        Result result = ItemModule.getItemList(itemService, itemImageService, tagsService);
        System.out.println(result);
        return result.toString();
    }

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
