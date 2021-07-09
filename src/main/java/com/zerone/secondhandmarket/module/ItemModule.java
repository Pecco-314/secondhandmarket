package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.enums.ItemCheckCondition;
import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.ItemFilter;
import com.zerone.secondhandmarket.message.SellingItemMessage;
import com.zerone.secondhandmarket.message.SellingItemModificationMessage;
import com.zerone.secondhandmarket.service.ItemImageService;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.TagsService;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;
import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemModule {
    //获取所有物品（对于管理员来说），用于审核用户发布的物品
    public static Result getItemList(ItemService itemService,ItemImageService itemImageService,TagsService tagsService) {
        List<Item> itemList = itemService.getItemList();
        getItemTagsAndImages(itemImageService,tagsService,itemList);
        return new Result(Status.ITEM_OK, "获取全部物品成功", itemList);
    }

    //获取所有审核通过的所有物品
    /*public static Result getCheckedItemList(ItemService itemService,ItemImageService itemImageService,TagsService tagsService){
        List<Item> itemList = itemService.getItemList();
        List<Item> checkedItems=getCheckedItems(itemList);
        getItemTagsAndImages(itemImageService,tagsService,checkedItems);

        return new Result(Status.ITEM_OK,"获取全部物品成功",checkedItems);
    }*/

    //根据类型筛选
    public static Result getItemsByFilter(ItemService service,ItemImageService itemImageService,TagsService tagsService, ItemFilter filter) {
        //获取所有符合条件的物品
        List<Item> list = service.getItemByFilter(filter);

        //List<SimplifiedItem> list = service.getItemByFilter(filter);
        //写完了Service的查询方案后改为这句

        if (list == null || list.isEmpty()) {
            return new Result(Status.ITEM_ERROR, "无符合条件物品", null);
        }
        //获取Item的图片和标签
        getItemTagsAndImages(itemImageService,tagsService,list);

        return new Result(Status.ITEM_OK, "获得所需物品", list);
    }

    //根据关键词筛选
    public static Result getCheckedItemsByKeyWords(ItemService service,ItemImageService itemImageService,TagsService tagsService,String keyword){
        //按关键词获得符合条件的物品
        List<Item> list=service.getItemByKeyword(keyword);
        //获得审核通过的物品
        List<Item> checkedList = getCheckedItems(list);
        //获得Item的图片和标签
        getItemTagsAndImages(itemImageService,tagsService,checkedList);

        return new Result(Status.ITEM_OK, "搜索成功", checkedList);
    }

    //获取物品详情
    public static Result getItemInfo(ItemService itemService, ItemImageService itemImageService,TagsService tagsService ,int itemId) {
        Item item = itemService.getItemById(itemId);

        if (item == null) {
            return new Result(Status.ITEM_ERROR, "无此物品", null);
        }
        //获取物品的图片和标签
        getItemTagsAndImages(itemImageService,tagsService,item);
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
        //设置物品信息
        //TODO:不知道要更新哪些信息
        try {
            itemService.updateItem(item);
            return new Result(Status.ITEM_OK, "更新物品成功", null);
        } catch (Exception e) {
            return new Result(Status.ITEM_ERROR, "更新物品失败", null);
        }
    }

    public static Result deleteUserItem(ItemService itemService,ItemImageService itemImageService,TagsService tagsService, int itemId) {
        try {
            itemService.deleteItem(itemId);
//            //删除对应的images
//            List<String> images= itemImageService.getImagesByItemId(itemId);
//            for(String image:images){
//                itemImageService.deleteItemImage(itemId,image);
//            }
//            //删除对应的tags
//            List<String> tags=tagsService.getTagsByItemId(itemId);
//            for(String tag:tags){
//                tagsService.deleteItem(itemId,tag);
//            }

            return new Result(Status.ITEM_OK, "删除物品成功", null);
        } catch (Exception e) {
            return new Result(Status.ITEM_ERROR, "", null);
        }
    }

    private static List<Item> getCheckedItems(List<Item> items){
        List<Item> checkedItems=new ArrayList<Item>() ;

        for(Item item:items){
            if(item.getCheckCondition()==ItemCheckCondition.TRUE)
                checkedItems.add(item);
        }

        return checkedItems;
    }

    private static void getItemTagsAndImages(ItemImageService itemImageService ,TagsService tagsService,Item item){
        item.setItemImages(itemImageService.getImagesByItemId(item.getId()));
        item.setItemTags(tagsService.getTagsByItemId(item.getId()));
    }

    private static void getItemTagsAndImages(ItemImageService itemImageService ,TagsService tagsService,List<Item> items){
        for(Item item:items) {
            item.setItemImages(itemImageService.getImagesByItemId(item.getId()));
            item.setItemTags(tagsService.getTagsByItemId(item.getId()));
        }
    }
}
