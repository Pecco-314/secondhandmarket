package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemCheckCondition;
import com.zerone.secondhandmarket.message.ItemCheckMessage;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@Controller("AdminItem")
public class ItemController {
    @Autowired
    private ItemService itemService = new ItemService();

    public String getItemUnchecked() {
        ItemFilter filter = new ItemFilter(null, null, null, ItemCheckCondition.UNCHECKED);

        Result result = ItemModule.getItemList(itemService, filter);

        return JSONMapper.writeValueAsString(result);
    }

    public String checkItem(@RequestBody ItemCheckMessage message) {
        Item item = itemService.getItemById(message.getItemID());

        item.setCheckCondition(message.getCheckCondition());

        Result result = ItemModule.modifyUserItem(itemService, item);

        return JSONMapper.writeValueAsString(result);
    }
}
