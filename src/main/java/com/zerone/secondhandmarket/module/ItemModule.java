package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.tools.Validation;
import com.zerone.secondhandmarket.viewobject.ResultVo;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;

import java.util.List;

public class ItemModule {
    /*public static ResultVo getItemList(ItemService itemService) {
        List<Item> itemList = itemService.getItemList();
        return new ResultVo(Status.OK, "获取全部物品成功", itemList);
    }*/

    public static ResultVo getItemList(ItemService service, ItemFilter filter) {
        List<Item> list = service.getItemByFilter(filter);

        if (list == null || list.isEmpty()) {
            return new ResultVo(Status.NO_QUALIFIED_ITEMS, "", null);
        }

        return new ResultVo(Status.ITEM_LIST_GOT, "", list);
    }

    public static ResultVo getItemInfo(ItemService itemService, int itemId) {
        Item item = itemService.getItemById(itemId);

        if (item == null)
            return new ResultVo(Status.NO_SUCH_ITEM, "", null);

        return new ResultVo(Status.ITEM_INFO_GOT, "", item);
    }

    public ResultVo releaseUserItem(ItemService itemService, Item item) {
        try {
            Validation.checkItemInfo(item);
            itemService.insertItem(item);
            return new ResultVo(Status.OK, "物品发布成功", null);
        } catch (InvalidInfoException e) {
            return new ResultVo(Status.INVALID_ITEM, e.getMessage(), null);
        } catch (Exception e) {
            return new ResultVo(Status.RELEASE_ITEM_ERROR, "", null);
        }
    }

    public ResultVo modifyUserItem(ItemService itemService, Item item) {
        try {
            Validation.checkItemInfo(item);
            itemService.updateItem(item);
            return new ResultVo(Status.OK, "", null);
        } catch (InvalidInfoException e) {
            return new ResultVo(Status.INVALID_ITEM, e.getMessage(), null);
        } catch (Exception e) {
            return new ResultVo(Status.RELEASE_ITEM_ERROR, "", null);
        }
    }

    public ResultVo deleteUserItem(ItemService itemService, int itemId) {
        try {
            itemService.deleteItem(itemId);
            return new ResultVo(Status.OK, "删除物品成功", null);
        } catch (Exception e) {
            return new ResultVo(Status.DELETE_ITEM_ERROR, "", null);
        }
    }
}
