package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加订单信息
    public int insertOrder(Order order) {
        String sql = "insert into orders(buyer_id, seller_id, item_id, quantity, ordering_time)" +
                "values(:buyer_id,:seller_id,:item_id,:quantity,:ordering_time)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", order.getItem_id());
        param.put("seller_id", order.getSeller_id());
        param.put("buyer_id", order.getBuyer_id());
        param.put("quantity", order.getQuantity());
        param.put("ordering_time", order.getOrdering_time());
        return jdbcTemplate.update(sql, param);
    }


    // 用于删除订单信息
    public int deleteOrder(int orderId) {
        String sql = "delete from orders where order_id=:order_id";
        Map<String, Object> param = new HashMap<>();
        param.put("order_id", orderId);
        return jdbcTemplate.update(sql, param);

    }

    // 用于更新订单
    public int updateOrder(Order order) {
        String sql = "update orders set buyer_id=:buyer_id,seller_id=:seller_id,item_id=:item_id,quantity=:quantity,ordering_time=:ordering_time where order_id=:order_id";
        Map<String, Object> param = new HashMap<>();

        param.put("item_id", order.getItem_id());
        param.put("seller_id", order.getSeller_id());
        param.put("buyer_id", order.getBuyer_id());
        param.put("quantity", order.getQuantity());
        param.put("ordering_time", order.getOrdering_time());
        return jdbcTemplate.update(sql, param);
    }

    // 通过itemid查询订单信息
    public Order getOrderByOrderId(int orderId) {
        String sql = "select * from orders where order_id=:order_id";
        Map<String, Object> param = new HashMap<>();
        param.put("order_id", orderId);
        Order order;
        try {
            order = jdbcTemplate.queryForObject(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
        return order;
    }

    // 通过关键字查询订单信息
    public List<Order> getOrderByKeyword(String keyword) {
        String sql = "select * from orders where item_id in (select item_id from item where item_name LIKE :item_name)";
        Map<String, Object> param = new HashMap<>();
        String str = "%" + keyword + "%";
        param.put("item_name", str);
        List<Order> orders;
        try {
            orders = jdbcTemplate.query(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
        return orders;
    }

    // 通过userid查询订单信息
    public List<Order> getOrderByUserId(int userId) {
        String sql = "select * from orders where buyer_id=:buyer_id";
        Map<String, Object> param = new HashMap<>();
        param.put("buyer_id", userId);
        List<Order> orders;
        try {
            orders = jdbcTemplate.query(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
        return orders;
    }

}
