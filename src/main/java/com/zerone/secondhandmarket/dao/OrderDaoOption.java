package com.zerone.secondhandmarket.dao;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.mapper.CountRowMapper;
import com.zerone.secondhandmarket.mapper.OrderRowMapper;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.tools.IndexGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoOption {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    // 用于添加订单信息,返回order_id
    public int insertOrder(Order order) {
        String sql = "insert into orders(buyer_id, seller_id, item_id, quantity, ordering_time, receiverName, phoneNumber, campus, dorm, detailed_address,state)" +
                "values(:buyer_id, :seller_id, :item_id, :quantity, :ordering_time, :receiverName, :phoneNumber, :campus, :dorm, :detailed_address,:state)";
        Map<String, Object> param = new HashMap<>();
        SqlParameterSource parameters = new MapSqlParameterSource().addValue("item_id", order.getItem()).addValue("buyer_id", order.getBuyer())
                .addValue("seller_id", order.getSeller()).addValue("ordering_time", order.getTime())
                .addValue("quantity", order.getQuantity()).addValue("phoneNumber", order.getPhoneNumber())
                .addValue("receiverName", order.getReceiverName()).addValue("dorm", order.getDorm())
                .addValue("campus", order.getCampus()).addValue("detailed_address", order.getDetailedAddress())
                .addValue("state", order.getState().toString());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, parameters, keyHolder, new String[]{"order_id"});
        return keyHolder.getKey().intValue();
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
        param.put("state", order.getState().toString());
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

    public Integer getOrderCount(OrderFilter filter) {
        StringBuilder sql = new StringBuilder(500);

        Map<String, Object> param = new HashMap<>();

        sql.append("select COUNT(*) _count from orders");

        generateExpression(filter, sql, param);

        try {
            return jdbcTemplate.query(sql.toString(), param, new CountRowMapper()).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Order> getOrderListByFilter(OrderFilter filter) {
        StringBuilder sql = new StringBuilder(500);

        Map<String, Object> param = new HashMap<>();

        sql.append("select * from orders");

        generateExpression(filter, sql, param);

        if(filter.getPage() != null) {
            sql.append(" limit :start,:count");
            param.put("start", IndexGenerator.generateStartIndex(filter.getPage(), false));
            param.put("count", IndexGenerator.countPerPage);
        }

        try {
            return jdbcTemplate.query(sql.toString(), param, new OrderRowMapper());
        } catch (Exception e) {
            return null;
        }
    }

    private static void generateExpression(OrderFilter filter, StringBuilder sql, Map<String, Object> param) {
        if (filter.getOrderId() != null) {
            sql.append(" where order_id=:order_id");
            param.put("order_id", filter.getOrderId());
            return;
        }
        boolean has_where = false;

        if (filter.getBuyer() != null) {
            sql.append(" where buyer_id=:buyer_id");
            has_where = true;

            param.put("buyer_id", filter.getBuyer());
        }

        if (filter.getSeller() != null) {
            if (!has_where) {
                sql.append(" where seller_id=:seller_id");
                has_where = true;
            } else {
                sql.append(" and seller_id=:seller_id");
            }
            param.put("seller_id", filter.getSeller());
        }

        if (filter.getItem() != null) {
            if (!has_where) {
                sql.append(" where item_id=:item_id");
                has_where = true;
            } else {
                sql.append(" and item_id=:item_id");
            }
            param.put("item_id", filter.getItem());
        }

        if (filter.getState() != null) {
            if (!has_where) {
                sql.append(" where state=:state");
            } else {
                sql.append(" and state=:state");
            }
            param.put("state", filter.getState().toString());
        }

        sql.append(" order by order_id desc");
    }

}
