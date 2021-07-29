package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.CartDao;
import com.zerone.secondhandmarket.dao.CartDaoOption;
import com.zerone.secondhandmarket.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService implements CartDao {
    @Autowired
    private CartDaoOption daoOption;

    @Override
    // 用于添加购物车信息
    public int insertCart(Cart cart) {
        return daoOption.insertCart(cart);
    }

    @Override
    // 用于删除购物车信息
    public int deleteCart(int userId, int itemId) {
        return daoOption.deleteCart(userId, itemId);
    }

    @Override
    //用于某个用户的清空购物车
    public int clearCart(int userId) {
        return daoOption.clearCart(userId);
    }

    @Override
    // 用于更新购物车数量信息
    public int modifyItemQuantity(Cart cart) {
        return daoOption.modifyItemQuantity(cart);
    }

    @Override
    public int insertOrUpdateCart(Cart cart, boolean accumulate) {
        System.out.println(cart);
        return daoOption.insertOrUpdateCart(cart, accumulate);
    }

    @Override
    public int accumulateItemQuantity(Cart cart) {
        return daoOption.accumulateItemQuantity(cart);
    }

    @Override
    public Integer getCartCount(int userId) {
        return daoOption.getCartCount(userId);
    }

    @Override
    //查询用户购物车信息
    public List<Cart> getCartListByUserId(int userId, Integer page) {
        return daoOption.getCartListByUserId(userId, page);
    }
}
