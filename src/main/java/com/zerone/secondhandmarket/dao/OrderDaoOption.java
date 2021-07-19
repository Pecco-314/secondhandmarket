package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.mapper.ItemRowMapper;
import com.zerone.secondhandmarket.mapper.OrderRowMapper;
import com.zerone.secondhandmarket.message.OrderFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class OrderDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    // 用于添加订单信息
    public int insertOrder(Order order) {
        String sql = "insert into orders(buyer_id, seller_id, item_id, quantity, ordering_time, receiverName, phoneNumber, campus, dorm, detailed_address,state)" +
                "values(:buyer_id, :seller_id, :item_id, :quantity, :ordering_time, :receiverName, :phoneNumber, :campus, :dorm, :detailed_address,:state)";
        Map<String, Object> param = new HashMap<>();
        param.put("item_id", order.getItem());
        param.put("seller_id", order.getSeller());
        param.put("buyer_id", order.getBuyer());
        param.put("quantity", order.getQuantity());
        param.put("ordering_time", order.getTime());
        param.put("receiverName", order.getReceiverName());
        param.put("phoneNumber", order.getPhoneNumber());
        param.put("campus", order.getCampus());
        param.put("dorm", order.getDorm());
        param.put("detailed_address", order.getDetailedAddress());
        param.put("state", order.getState().toString());
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
        String sql = "update orders set buyer_id=:buyer_id,seller_id=:seller_id,item_id=:item_id,quantity=:quantity,ordering_time=:ordering_time,receiverName=:receiverName,phoneNumber=:phoneNumber,campus=:campus,dorm=:dorm,detailed_address=:detailed_address,state=:state where order_id=:order_id";
        Map<String, Object> param = new HashMap<>();

        param.put("order_id", order.getId());
        param.put("item_id", order.getItem());
        param.put("seller_id", order.getSeller());
        param.put("buyer_id", order.getBuyer());
        param.put("quantity", order.getQuantity());
        param.put("ordering_time", order.getTime());
        param.put("receiverName", order.getReceiverName());
        param.put("phoneNumber", order.getPhoneNumber());
        param.put("campus", order.getCampus());
        param.put("dorm", order.getDorm());
        param.put("state", order.getState());
        param.put("detailed_address", order.getDetailedAddress());
        return jdbcTemplate.update(sql, param);
    }

    // 通过itemid查询订单信息
    public Order getOrderByOrderId(int orderId) {
        String sql = "select * from orders where order_id=:order_id";
        Map<String, Object> param = new HashMap<>();
        param.put("order_id", orderId);
        try {
            return jdbcTemplate.queryForObject(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // 通过关键字查询订单信息
    public List<Order> getOrderByKeyword(String keyword) {
        String sql = "select * from orders where item_id in (select item_id from item where item_name LIKE :item_name)";
        Map<String, Object> param = new HashMap<>();
        String str = "%" + keyword + "%";
        param.put("item_name", str);
        try {
            return jdbcTemplate.query(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    // 通过userid查询订单信息
    public List<Order> getOrderByUserId(int userId, boolean isBuyer) {
        String sql = String.format("select * from orders where %s=:user_id", isBuyer ? "buyer_id" : "seller_id");
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        try {
            return jdbcTemplate.query(sql, param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Order> getOrderListByFilter(OrderFilter filter) {
        StringBuilder sql = new StringBuilder(500);

        boolean has_where = false;

        Map<String, Object> param = new HashMap<>();

        sql.append("select * from orders");

        if(filter.getBuyer() != null) {
            sql.append(" where buyer_id=:buyer_id");
            has_where = true;

            param.put("buyer_id", filter.getBuyer());
        }

        if (filter.getSeller() != null) {
            if(!has_where) {
                sql.append(" where seller_id=:seller_id");
                has_where = true;
            } else {
                sql.append(" and seller_id=:seller_id");
            }
            param.put("seller_id", filter.getSeller());
        }

        if(filter.getItem() != null) {
            if(!has_where) {
                sql.append(" where item_id=:item_id");
                //has_where = true;
            } else {
                sql.append(" and item_id=:item_id");
            }
            param.put("item_id", filter.getItem());
        }

        try {
            return jdbcTemplate.query(sql.toString(), param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

}
