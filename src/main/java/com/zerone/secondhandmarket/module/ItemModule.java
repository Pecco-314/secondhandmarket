package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;

import java.util.List;

public class ItemModule {
    /*public static ResultVo getItemList(ItemService itemService) {
        List<Item> itemList = itemService.getItemList();
        return new ResultVo(Status.OK, "获取全部物品成功", itemList);
    }*/

    public static Result getItemList(ItemService service, ItemFilter filter) {
        List<Item> list = service.getItemByFilter(filter);
        //List<SimplifiedItem> list = service.getItemByFilter(filter);
        //写完了Service的查询方案后改为这句

        if (list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ITEMS, "", null);
        }

        return new Result(Status.ITEM_LIST_GOT, "", list);
    }

    public static Result getItemInfo(ItemService itemService, int itemId) {
        Item item = itemService.getItemById(itemId);

        if (item == null)
            return new Result(Status.NO_SUCH_ITEM, "", null);

        return new Result(Status.ITEM_INFO_GOT, "", item);
    }

    public Result releaseUserItem(ItemService itemService, Item item) {
        try {
            itemService.insertItem(item);
            return new Result(Status.OK, "物品发布成功", null);
        } catch (Exception e) {
            return new Result(Status.RELEASE_ITEM_ERROR, "", null);
        }
    }

    public Result modifyUserItem(ItemService itemService, Item item) {
        try {
            itemService.updateItem(item);
            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            return new Result(Status.RELEASE_ITEM_ERROR, "", null);
        }
    }

    public Result deleteUserItem(ItemService itemService, int itemId) {
        try {
            itemService.deleteItem(itemId);
            return new Result(Status.OK, "删除物品成功", null);
        } catch (Exception e) {
            return new Result(Status.DELETE_ITEM_ERROR, "", null);
        }
    }
}
