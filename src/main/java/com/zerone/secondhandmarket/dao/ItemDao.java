package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.SimplifiedItem;
import com.zerone.secondhandmarket.enums.ItemType;
import com.zerone.secondhandmarket.enums.Ordering;
import com.zerone.secondhandmarket.message.ItemFilter;

import java.util.List;

public interface ItemDao {
    // 用于添加商品
    int insertItem(Item item);

    // 用于删除商品
    int deleteItem(int itemId);

    // 用于更新商品
    int updateItem(Item item);

    /*// 通过类型查询商品
    List<Item> getItemByType(ItemType itemType);*/
    // 通过id查询商品
    Item getItemById(int itemId);

    /*// 通过类似商品名（关键字）查询商品
    List<Item> getItemByKeyword(String keyword);*/

    // 用于查询所有商品列表
    List<Item> getItemList();

    /*//按价格排序获取商品列表
    List<Item> getItemListOrderByPrice(Ordering ordering);*/

    List<Item> getItemListByFilter(ItemFilter itemFilter);

}
