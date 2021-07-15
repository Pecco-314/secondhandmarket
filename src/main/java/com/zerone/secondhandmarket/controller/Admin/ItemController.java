package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.message.ItemCheckMessage;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller("AdminItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();
    @Autowired
    private ItemImageService itemImageService = new ItemImageService();
    @Autowired
    private TagsService tagsService = new TagsService();

    @RequestMapping("/admin-item")
    public String openAdminItemPage() {
        return "tables-goods";
    }


    @ResponseBody
    @GetMapping("/requests/admin/items")
    public String getAllItems() {
        ItemFilter filter = new ItemFilter(null, null, null, null, null);

        Result result = ItemModule.getItemsByFilter(itemService, itemImageService, tagsService, filter);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/admin/checkItem")
    public String checkItem(@RequestBody ItemCheckMessage message) {
        Item item = itemService.getItemById(message.getItemID());
        item.setCheckCondition(message.getCheckCondition());

        Result result = ItemModule.modifyUserItem(itemService, itemImageService, tagsService, item);

        return result.toString();
    }
}
