package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderModule {
    public static Result getOrderList(OrderService service, OrderFilter filter) {
        List<Order> list = service.getOrderByFilter(filter);

        if(list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ORDERS, "没有合适的订单", null);
        }

        return new Result(Status.ORDER_OK, "获取订单列表成功", list);
    }

    public static Result generateOrder(OrderService service, Integer buyer, Integer seller, Integer itemId, Integer quantity) {
        try {
            Order order = new Order(0, buyer, seller, itemId, quantity, DateFormatter.dateToString(new Date()));

            service.insertOrder(order);

            return new Result(Status.ORDER_OK, "", null);
        } catch (Exception e) {
            return new Result(Status.GENERATE_ORDER_ERROR, "", null);
        }
    }

    public static Result cancelOrder(OrderService service, OrderFilter filter) {
        try {
            List<Order> list = service.getOrderByFilter(filter);

            if(list == null || list.size() != 1) {
                return new Result(Status.ERROR, "", null);
            }

            service.deleteOrder(list.get(0).getId());

            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            return new Result(Status.ERROR, "", null);
        }
    }
}
