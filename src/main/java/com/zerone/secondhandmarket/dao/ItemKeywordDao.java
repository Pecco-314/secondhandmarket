package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.enums.ItemType;

import java.util.List;

public interface ItemKeywordDao {
    // 用于添加商品关键词
    int insertKeyword(int item_id,String keyword);

    // 用于删除关键词
    int deleteItem(int itemId,String keyword);

    // 通过类型查询商品
    List<String> getKeywordsByItemId(int itemId);
}
