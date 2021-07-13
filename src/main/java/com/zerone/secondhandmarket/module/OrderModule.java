package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.Date;
import java.util.List;

public class OrderModule {
    public static Result getOrderList(OrderService service, OrderFilter filter) {
        List<Order> list = service.getOrderByFilter(filter);

        if (list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ORDERS, "没有合适的订单", null);
        }

        return new Result(Status.ORDER_OK, "获取订单列表成功", list);
    }

    public static Result generateOrder(OrderService service, Integer buyer, Integer seller, Integer itemId, Integer quantity, String receiverName, String phoneNumber, String campus, String dorm, String detailedAddress) {
        try {
            Order order = new Order(0, buyer, seller, itemId, quantity, DateFormatter.dateToString(new Date()), receiverName, phoneNumber, campus, dorm, detailedAddress,"待收货");

            service.insertOrder(order);

            return new Result(Status.ORDER_OK, "生成订单成功", null);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return new Result(Status.GENERATE_ORDER_ERROR, "生成订单失败", null);
        }
    }

    public static Result cancelOrder(OrderService service, OrderFilter filter) {
        try {
            List<Order> list = service.getOrderByFilter(filter);

            if (list == null || list.size() != 1) {
                return new Result(Status.ERROR, "", null);
            }

            service.deleteOrder(list.get(0).getId());

            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            return new Result(Status.ERROR, "", null);
        }
    }
}
