package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.viewobject.Result;

import java.util.Date;
import java.util.List;

public class OrderModule {
    public Result getOrderList(OrderService service, OrderFilter filter) {
        List<Order> list = service.getOrderByFilter(filter);

        if(list == null || list.isEmpty())
            return new Result(Status.NO_QUALIFIED_ORDERS, "", null);

        return new Result(Status.OK, "", list);
    }

    public Result generateOrder(OrderService service, OrderMessage message) {
        try {
            Order order = new Order(0, message.getBuyer(), message.getSeller(), message.getItemID(), message.getQuantity(), new Date().toString());
            service.insertOrder(order);

            return new Result(Status.OK, "", null);
        } catch (Exception e) {
            return new Result(Status.GENERATE_ORDER_ERROR, "", null);
        }
    }

    public Result cancelOrder(OrderFilter filter) {
        return null;
    }
}