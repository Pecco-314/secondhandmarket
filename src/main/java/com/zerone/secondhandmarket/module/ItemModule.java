package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.viewobject.ResultVo;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.viewobject.Status;

import java.util.List;

public class ItemModule {
    public static ResultVo getItemList(ItemService itemService) {
        List<Item> itemList = itemService.getItemList();
        return new ResultVo(Status.OK, "获取全部物品成功", itemList);
    }

    public static ResultVo getFilteredItemList(ItemService itemService, ItemFilter itemFilter) {
        List<Item> itemList = itemService.getItemByFilter(itemFilter);
        return new ResultVo(Status.OK, "筛选物品成功", itemList);
    }

    public static ResultVo getItemInfo(ItemService itemService, int productId) {
        Item item = itemService.getItemById(productId);
        return new ResultVo(Status.OK, "获取物品成功", item);
    }

    public ResultVo addUserItem(ItemService itemService, Item item) {
        itemService.insertItem(item);
        return new ResultVo(Status.OK, "物品发布成功", null);
    }

    public ResultVo modifyUserItem(ItemService itemService, Item item) {
        itemService.updateItem(item);
        return new ResultVo(Status.OK, "修改物品成功", null);
    }

    public ResultVo deleteUserItem(ItemService itemService, int itemId) {
        itemService.deleteItem(itemId);
        return new ResultVo(Status.OK, "删除物品成功", null);
    }
}
