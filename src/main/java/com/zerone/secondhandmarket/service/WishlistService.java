package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.CartDao;
import com.zerone.secondhandmarket.dao.CartDaoOption;
import com.zerone.secondhandmarket.dao.WishlistDao;
import com.zerone.secondhandmarket.dao.WishlistDaoOption;
import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishlistService implements WishlistDao {
    @Autowired
    private WishlistDaoOption daoOption;

    @Override
    // 用于添加收藏夹信息
    public int insertWishlist(Wishlist wishlist) {
        return daoOption.insertWishlist(wishlist);
    }

    @Override
    // 用于删除收藏夹信息
    public int deleteWishlist(int userId, int itemId) {
        return daoOption.deleteWishlist(userId, itemId);
    }

    @Override
    //用于某个用户的清空收藏夹
    public int clearWishlist(int userId) {
        return daoOption.clearWishlist(userId);
    }

    @Override
    public Wishlist getWishlistByKey(int userId, int itemId) {
        return daoOption.getWishlistByKey(userId, itemId);
    }

    @Override
    //查询用户收藏夹信息
    public List<Wishlist> getWishlistByUserId(int userId) {
        return daoOption.getWishlistByUserId(userId);
    }
}

