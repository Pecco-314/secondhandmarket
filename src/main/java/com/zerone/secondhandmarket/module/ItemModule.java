package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;

import java.util.List;

public class ItemModule {
    //获取所有物品
    public static Result getItemList(ItemService itemService) {
        List<Item> itemList = itemService.getItemList();
        return new Result(Status.OK, "获取全部物品成功", itemList);
    }
    //根据类型筛选
    public static Result getItemList(ItemService service, ItemFilter filter) {
        List<Item> list = service.getItemByFilter(filter);
        //List<SimplifiedItem> list = service.getItemByFilter(filter);
        //写完了Service的查询方案后改为这句

        if (list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ITEMS, "", null);
        }

        return new Result(Status.ITEM_LIST_GOT, "", list);
    }
    //获取物品详情
    public static Result getItemInfo(ItemService itemService, int itemId) {
        Item item = itemService.getItemById(itemId);

        if (item == null) {
            return new Result(Status.NO_SUCH_ITEM, "", null);
        }

        return new Result(Status.ITEM_INFO_GOT, "", item);
    }

    public static Result releaseUserItem(ItemService itemService, TagsService tagsService, ItemImageService itemImageService, SellingItemMessage sellingItemMessage) {
        Item item=new Item();
        try {
            //设置物品信息
            item.setSeller(sellingItemMessage.getSeller());
            item.setName(sellingItemMessage.getName());
            item.setType(sellingItemMessage.getType());
            item.setQuantity(sellingItemMessage.getQuantity());
            item.setPrice(sellingItemMessage.getPrice());
            if(sellingItemMessage.getOriginalPrice()!=null)
                item.setOriginalPrice(sellingItemMessage.getOriginalPrice());
            if(sellingItemMessage.getIntroduction()!=null)
                item.setIntroduction(sellingItemMessage.getIntroduction());
            //若有传图片，暂时以第一张照片作为封面
            if(sellingItemMessage.getImages().length!=0) {
                //System.out.println((String)sellingItemMessage.getImages()[0].getData());
                item.setCoverPath(sellingItemMessage.getImages()[0]);
            }

            //插入物品
//            int id=itemService.insertItem(item);

            //插入标签信息
//          for(String tag:sellingItemMessage.getTags()) {
//                tagsService.insertTag(sellingItemMessage.getSeller(),tag);
//          }

            //插入图片信息
//            for(String imagePath:sellingItemMessage.getImages()){
//             itemImageService.insertItemImage(id,imagePath);
//          }

            return new Result(Status.RELEASE_ITEM_SUCCESS, "物品发布成功", item);
        } catch (Exception e) {
            return new Result(Status.RELEASE_ITEM_ERROR, "物品发布失败", item);
        }
    }

    public static Result modifyUserItem(ItemService itemService, Item item) {
        try {
            itemService.updateItem(item);
            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            return new Result(Status.RELEASE_ITEM_ERROR, "", null);
        }
    }

    public static Result deleteUserItem(ItemService itemService, int itemId) {
        try {
            itemService.deleteItem(itemId);
            return new Result(Status.OK, "删除物品成功", null);
        } catch (Exception e) {
            return new Result(Status.DELETE_ITEM_ERROR, "", null);
        }
    }
}
