package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.OrderState;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
import com.zerone.secondhandmarket.tools.DateFormatter;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("OrdinaryIndent")
public class OrderController {
    @Autowired
    private OrderService orderService = new OrderService();
    @Autowired
    private ItemService itemService = new ItemService();

    //获取用户订单列表
    @ResponseBody
    @GetMapping("/requests/user/orderList/buyer/{userId}")
    public String getOrderListAsBuyer(@PathVariable int userId) {
        Result result = OrderModule.getOrderList(orderService, userId, true);

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/user/orderList/seller/{userId}")
    public String getOrderListAsSeller(@PathVariable int userId) {
        Result result = OrderModule.getOrderList(orderService, userId, false);

        return result.toString();
    }

    @ResponseBody
    @GetMapping("/requests/user/orderList/search")
    public String searchOrder(@RequestBody OrderFilter filter) {
        Result result = OrderModule.getOrderListByFilter(orderService, filter);

        return result.toString();
    }

    //生成单个订单
    @ResponseBody
    @PostMapping("/requests/user/insertOrder")
    public String generateSingleOrder(@RequestBody OrderMessage order) {
        if (CodeProcessor.validateIdToken(order.getBuyer(), order.getToken())) {
            int seller = itemService.getItemById(order.getItemID()).getSeller();

            Order newOrder = new Order(0, order.getBuyer(), seller, order.getItemID(), order.getQuantity(), null,
                    order.getReceiverName(), order.getPhoneNumber(),
                    order.getCampus(), order.getDorm(), order.getDetailedAddress(), null);

            Result result = OrderModule.generateOrder(orderService, newOrder);

            return result.toString();
        } else {
            return new Result(Status.USER_ERROR, "id与token不一致", null).toString();
        }

    }

    //更新订单状态
    @ResponseBody
    @GetMapping("requests/user/orderChecked/{orderId}")
    public String updateOrderState(@PathVariable int orderId) {
        Order newOrder = orderService.getOrderByOrderId(orderId);
        newOrder.setState(OrderState.FINISHED);

        Result result = OrderModule.updateOrder(orderService, newOrder);

        return result.toString();
    }

    //取消订单
    @ResponseBody
    @PostMapping("/requests/user/cancelOrder")
    public String cancelSingleOrder(@RequestBody OrderFilter filter) {
        Result result = OrderModule.cancelOrder(orderService, filter);

        return result.toString();
    }
}
