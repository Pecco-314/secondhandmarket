package com.zerone.secondhandmarket.dao;

import java.util.List;

public interface ItemImageDao {
    // 用于添加商品图片
    int insertItemImage(int itemId, String imagePath);

    // 用于删除商品图片
    int deleteItemImage(int itemId, String imagePath);

    // 通过类型查询商品图片
    List<String> getImagesByItemId(int itemId);
}
