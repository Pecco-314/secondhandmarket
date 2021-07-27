package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Wishlist;

import java.util.List;

public interface WishlistDao {
    // 用于添加收藏夹信息
    int insertWishlist(Wishlist wishlist);
    // 用于删除收藏夹信息
    int deleteWishlist(int userId,int itemId);
    //用于某个用户的清空收藏夹
    int clearWishlist(int userId);
    Wishlist getWishlistByKey(int userId,int itemId);
    //查询用户收藏夹信息
    List<Wishlist> getWishlistByUserId(int userId, Integer page);

    Integer getWishlistCount(int userId);
}
