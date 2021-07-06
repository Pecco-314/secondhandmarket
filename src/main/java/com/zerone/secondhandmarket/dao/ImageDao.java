package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Cart;

import java.util.List;

public interface ImageDao {
    // 用于添加图片信息
    int insertImage(String imagePath);

    // 用于删除图片信息
    int deleteImage(String imagePath);
}
