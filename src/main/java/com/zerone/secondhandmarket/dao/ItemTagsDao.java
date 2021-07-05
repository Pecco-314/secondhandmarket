package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemType;

import java.util.List;

public interface ItemTagsDao {
    // 用于添加商品关键词
    int insertTag(int itemId, String tag);

    // 用于删除关键词
    int deleteItem(int itemId, String tag);

    // 通过类型查询商品
    List<String> getTagsByItemId(int itemId);
}
