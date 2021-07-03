package com.zerone.secondhandmarket.service;

import com.zerone.secondhandmarket.dao.ShoppingCartDao;
import com.zerone.secondhandmarket.dao.ShoppingCartDaoOption;
import com.zerone.secondhandmarket.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoppingCartService implements ShoppingCartDao {
    @Autowired
    private ShoppingCartDaoOption daoOption;

    @Override
    // 用于添加购物车信息
    public int insertCart(ShoppingCart cart) {
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
    public int modifyItemQuantity(ShoppingCart cart) {
        return daoOption.modifyItemQuantity(cart);
    }

    @Override
    //查询用户购物车信息
    public List<ShoppingCart> getCartListByUserId(int userId) {
        return daoOption.getCartListByUserId(userId);
    }
}
