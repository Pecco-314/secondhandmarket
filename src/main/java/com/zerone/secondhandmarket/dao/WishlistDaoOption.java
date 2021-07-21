package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Cart;
import com.zerone.secondhandmarket.entity.Wishlist;
import com.zerone.secondhandmarket.mapper.ShoppingCartRowMapper;
import com.zerone.secondhandmarket.mapper.WishlistRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishlistDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加收藏夹信息
    public int insertWishlist(Wishlist wishlist) {
        String sql = "insert into wishlist(user_id, item_id) values(:user_id,:item_id)";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", wishlist.getUserId());
        param.put("item_id", wishlist.getItemId());
        return jdbcTemplate.update(sql, param);
    }

    // 用于删除购物车信息
    public int deleteWishlist(int userId, int itemId) {
        String sql = "delete from wishlist where user_id=:user_id and item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("item_id", itemId);
        return jdbcTemplate.update(sql, param);
    }

    //用于某个用户的清空购物车
    public int clearWishlist(int userId) {
        String sql = "delete from wishlist where user_id=:user_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        return jdbcTemplate.update(sql, param);
    }

    public Wishlist getWishlistByKey(int userId,int itemId)
    {
        String sql = "select * from wishlist where user_id=:user_id and item_id=:item_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("item_id", itemId);
        try {
            return jdbcTemplate.queryForObject(sql, param, new WishlistRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
    //查询用户购物车信息
    public List<Wishlist> getWishlistByUserId(int userId) {
        String sql = "select * from wishlist where user_id=:user_id";
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        try {
            return jdbcTemplate.query(sql, param, new WishlistRowMapper());
        } catch (Exception e) {
            return null;
        }
    }
}
