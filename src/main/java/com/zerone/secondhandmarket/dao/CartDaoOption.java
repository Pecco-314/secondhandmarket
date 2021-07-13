package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.mapper.ShoppingCartRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加购物车信息
    public int insertCart(Cart cart) {
        String sql = "insert into shoppingcart(user_id, item_id, quantity) " +
                "values(:user_id,:item_id,:quantity)";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", cart.getUserId());
        param.put("item_id", cart.getItemId());
        param.put("quantity", cart.getQuantity());
        return  jdbcTemplate.update(sql, param);
    }

    // 用于删除购物车信息
    public int deleteCart(int userId, int itemId) {
        String sql = "delete from shoppingcart where user_id=:user_id and item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("item_id", itemId);
        jdbcTemplate.update(sql, param);
        return 0;
    }

    //用于某个用户的清空购物车
    public int clearCart(int userId) {
        String sql = "delete from shoppingcart where user_id=:user_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        jdbcTemplate.update(sql, param);
        return 0;
    }

    // 用于更新购物车物品数量信息
    public int modifyItemQuantity(Cart cart) {
        String sql = "update shoppingcart set quantity=:quantity  where user_id=:user_id and item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", cart.getUserId());
        param.put("item_id", cart.getItemId());
        param.put("quantity", cart.getQuantity());
        jdbcTemplate.update(sql, param);
        return 0;
    }
    //插入或更新购物车
    public int insertOrUpdateCart(Cart cart)
    {
        if (getCartByKey(cart.getUserId(),cart.getItemId()) != null)
            modifyItemQuantity(cart);
        else insertCart(cart);
        return 0;
    }
    public Cart getCartByKey(int userId,int itemId)
    {
        String sql = "select * from shoppingcart where user_id=:user_id and item_id=:item_id";
        Cart cart;
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("item_id", itemId);
        try {
            cart = jdbcTemplate.queryForObject(sql, param, new ShoppingCartRowMapper());
        } catch (Exception e) {
            return null;
        }
        return cart;
    }

    //查询用户购物车信息
    public List<Cart> getCartListByUserId(int userId) {
        String sql = "select * from shoppingcart where user_id=:user_id";
        List<Cart> carts;
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        try {
            carts = jdbcTemplate.query(sql, param, new ShoppingCartRowMapper());
        } catch (Exception e) {
            return null;
        }
        return carts;
    }
}
