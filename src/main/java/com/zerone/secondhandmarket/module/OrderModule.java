package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.OrderState;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderModule {
    public static Result getOrderList(OrderService service, int userId, boolean isBuyer) {
        List<Order> list = service.getOrderByUserId(userId, isBuyer);

        if (list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ORDERS, "没有合适的订单", null);
        }

        /*List<SimplifiedOrder> simplifiedOrderList = list
                .stream()
                .map(order -> new SimplifiedOrder(order, itemService.getItemById(order.getItem())))
                .collect(Collectors.toList());*/

        /*List<SimplifiedOrder> simplifiedOrderList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            simplifiedOrderList.add(new SimplifiedOrder(list.get(i), itemService.getItemById(list.get(i).getItem())));
        }*/

        return new Result(Status.ORDER_OK, "获取订单列表成功", list);
    }

    public static Result getOrderListByFilter(OrderService service, OrderFilter filter) {
        List<Order> list = service.getOrderListByFilter(filter);

        if (list == null || list.isEmpty()) {
            return new Result(Status.NO_QUALIFIED_ORDERS, "没有合适的订单", null);
        }

        return new Result(Status.ORDER_OK, "获取订单列表成功", list);
    }

    public static Result generateOrder(OrderService service, Order order) {
        try {
            order.setTime(DateFormatter.dateToString(new Date()));
            order.setState(OrderState.UNPAID);

            Integer orderId = service.insertOrder(order);

            return new Result(Status.ORDER_OK, "生成订单成功", orderId);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            return new Result(Status.GENERATE_ORDER_ERROR, "生成订单失败", null);
        }
    }

    public static Result updateOrder(OrderService orderService, Order order) {
        try {
            orderService.updateOrder(order);
            return new Result(Status.ORDER_OK, "订单更新成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(Status.NO_QUALIFIED_ORDERS, "订单更新失败", null);
        }
    }

    public static Result cancelOrder(OrderService service, OrderFilter filter) {
        try {
            List<Order> list = service.getOrderListByFilter(filter);

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
