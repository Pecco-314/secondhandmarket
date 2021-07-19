package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemCheckCondition;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ItemModule {
    private static final int itemsInHomepage = 8; //主页物品个数

    //获取主页物品
    public static Result getItemListForHomepage(ItemService itemService, ItemImageService itemImageService, TagsService tagsService) {
        List<Item> itemList = itemService.getItemList();

        int size = itemList.size();
        itemList = itemList.subList(Math.max(size - itemsInHomepage, 0), size);
        Collections.reverse(itemList);

        getItemTagsAndImages(itemImageService, tagsService, itemList, false, true);
        return new Result(Status.ITEM_OK, "获取全部物品成功", itemList);
    }

    /*//获取所有物品（对于管理员来说），用于审核用户发布的物品
    public static Result getItemList(ItemService itemService, ItemImageService itemImageService, TagsService tagsService) {
        List<Item> itemList = itemService.getItemList();
        getItemTagsAndImages(itemImageService, tagsService, itemList, true, true);
        return new Result(Status.ITEM_OK, "获取全部物品成功", itemList);
    }*/

    //根据类型筛选
    public static Result getItemsByFilter(ItemService service, ItemImageService itemImageService, TagsService tagsService, ItemFilter filter) {
        //获取所有符合条件的物品
        List<Item> list = service.getItemListByFilter(filter);

        if (list == null || list.isEmpty()) {
            return new Result(Status.ITEM_ERROR, "无符合条件物品", null);
        }

        getItemTagsAndImages(itemImageService, tagsService, list, filter.isImagesNeeded(), filter.isTagsNeeded());

        return new Result(Status.ITEM_OK, "获得所需物品", list);
    }

    /*//搜索物品
    public static Result searchItems(ItemService itemService, ItemImageService itemImageService, TagsService tagsService, ItemFilter itemFilter) {
        List<Item> list = itemService.getItemListByFilterAndKeyword(itemFilter, keyword);

        if (list == null || list.isEmpty()) {
            return new Result(Status.ITEM_ERROR, "无符合条件物品", null);
        }
        //获取Item的图片和标签
        getItemTagsAndImages(itemImageService, tagsService, list);

        return new Result(Status.ITEM_OK, "获得所需物品", list);
    }*/

    /*//根据关键词筛选
    public static Result getCheckedItemsByKeyWords(ItemService service, ItemImageService itemImageService, TagsService tagsService, String keyword) {
        //按关键词获得符合条件的物品
        List<Item> list = service.getItemByKeyword(keyword)
                .stream()
                .filter(item -> item.getCheckCondition() == ItemCheckCondition.TRUE)
                .collect(Collectors.toList());
        //获得Item的图片和标签
        getItemTagsAndImages(itemImageService, tagsService, list);

        return new Result(Status.ITEM_OK, "搜索成功", list);
    }*/

    //获取物品详情
    public static Result getItemInfo(ItemService itemService, ItemImageService itemImageService, TagsService tagsService, int itemId) {
        Item item = itemService.getItemById(itemId);

        if (item == null) {
            return new Result(Status.ITEM_ERROR, "无此物品", null);
        }
        //获取物品的图片和标签
        getItemTagsAndImages(itemImageService, tagsService, item, true, true);
        return new Result(Status.ITEM_OK, "获取物品成功", item);
    }

    public static Result releaseUserItem(ItemService itemService, TagsService tagsService, ItemImageService itemImageService, SellingItemMessage sellingItemMessage) {
        Item item = new Item();
        try {
            //设置物品信息
            item.setSeller(sellingItemMessage.getSeller());
            item.setName(sellingItemMessage.getName());
            item.setType(sellingItemMessage.getType());
            item.setQuantity(sellingItemMessage.getQuantity());
            item.setPrice(sellingItemMessage.getPrice());
            item.setReleaseTime(DateFormatter.dateToString(new Date()));
            //TODO：暂时使其审核通过，有了审核功能再改
            item.setCheckCondition(ItemCheckCondition.TRUE);

            if (sellingItemMessage.getOriginalPrice() != null)
                item.setOriginalPrice(sellingItemMessage.getOriginalPrice());
            if (sellingItemMessage.getIntroduction() != null)
                item.setIntroduction(sellingItemMessage.getIntroduction());
            //若有传图片，暂时以第一张照片作为封面
            if (sellingItemMessage.getImages().length != 0) {
                //System.out.println((String)sellingItemMessage.getImages()[0].getData());
                item.setCoverPath(sellingItemMessage.getImages()[0]);
            }
            item.setReleaseTime(DateFormatter.dateToString(new Date()));
            //插入物品
            int id = itemService.insertItem(item);

            //插入标签信息
            for (String tag : sellingItemMessage.getTags()) {
                tagsService.insertTag(id, tag);
            }

            //插入图片信息
            for (String imagePath : sellingItemMessage.getImages()) {
                System.out.printf("id:%d,imagePath:%s", id, imagePath);
                itemImageService.insertItemImage(id, imagePath);
            }

            return new Result(Status.ITEM_OK, "物品发布成功", item);
        } catch (Exception e) {
            return new Result(Status.ITEM_ERROR, "物品发布失败", item);
        }
    }


    public static Result modifyUserItem(ItemService itemService, ItemImageService itemImageService, TagsService tagsService, Item item) {
        try {
            itemService.updateItem(item);
            return new Result(Status.ITEM_OK, "更新物品成功", null);
        } catch (Exception e) {
            return new Result(Status.ITEM_ERROR, "更新物品失败", null);
        }
    }

    public static Result deleteUserItem(ItemService itemService, ItemImageService itemImageService, TagsService tagsService, int itemId) {
        try {
            itemService.deleteItem(itemId);

            return new Result(Status.ITEM_OK, "删除物品成功", null);
        } catch (Exception e) {
            return new Result(Status.ITEM_ERROR, "", null);
        }
    }

    private static void getItemTagsAndImages(ItemImageService itemImageService, TagsService tagsService, Item item, boolean imagesNeeded, boolean tagsNeeded) {
        if(imagesNeeded)
            item.setItemImages(itemImageService.getImagesByItemId(item.getId()));
        if(tagsNeeded)
            item.setItemTags(tagsService.getTagsByItemId(item.getId()));
    }

    private static void getItemTagsAndImages(ItemImageService itemImageService, TagsService tagsService, List<Item> items, boolean imagesNeeded, boolean tagsNeeded) {
        items.parallelStream().forEach(item -> {
            if(imagesNeeded)
                item.setItemImages(itemImageService.getImagesByItemId(item.getId()));
            if(tagsNeeded)
                item.setItemTags(tagsService.getTagsByItemId(item.getId()));
        });
    }
}
