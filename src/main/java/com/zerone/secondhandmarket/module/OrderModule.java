package com.zerone.secondhandmarket.module;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.exception.InvalidInfoException;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.Validation;
import com.zerone.secondhandmarket.viewobject.ResultVo;

import java.util.List;

public class OrderModule {
    public ResultVo getOrderList(OrderService service, OrderFilter filter) {
        List<Order> list = service.getOrderByFilter(filter);

        if(list == null || list.isEmpty())
            return new ResultVo(Status.NO_QUALIFIED_ORDERS, "", null);

        return new ResultVo(Status.OK, "", list);
    }

    public ResultVo generateOrder(OrderService service, OrderMessage message) {
        try {
            Validation.checkOrderInfo(message);

            Order order = new Order(message);
            service.insertOrder(order);

            return new ResultVo(Status.OK, "", null);
        } catch (InvalidInfoException e) {
            return new ResultVo(Status.INVALID_ORDER, e.getMessage(), null);
        } catch (Exception e) {
            return new ResultVo(Status.GENERATE_ORDER_ERROR, "", null);
        }
    }

    public ResultVo cancelOrder(OrderFilter filter) {
        return null;
    }
}
