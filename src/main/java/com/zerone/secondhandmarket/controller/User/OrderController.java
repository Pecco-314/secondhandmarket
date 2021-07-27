package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Item;
import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.enums.OrderState;
import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.message.OrderStateModificationMessage;
import com.zerone.secondhandmarket.message.UserTokenMessage;
import com.zerone.secondhandmarket.module.ItemModule;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.module.WishlistModule;
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
    @PostMapping("/requests/user/orderList/search")
    public String searchOrder(@RequestBody OrderFilter filter) {
        Result result = OrderModule.getOrderListByFilter(orderService, filter);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/user/orderList/count")
    public String getOrderListCount(@RequestBody OrderFilter filter) {
        Result result = OrderModule.getOrderListCount(orderService, filter);

        return result.toString();
    }

    //生成单个订单
    @ResponseBody
    @PostMapping("/requests/user/insertOrder")
    public String generateSingleOrder(@RequestBody OrderMessage order) {
        if (CodeProcessor.validateIdToken(order.getBuyer(), order.getToken())) {
            Item item = itemService.getItemById(order.getItemID());
            if(item == null)
                return new Result(Status.ORDER_ERROR, "无法获取物品", null).toString();
            int seller = item.getSeller();

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
    @PostMapping("requests/user/orderChecked")
    public String updateOrderState(@RequestBody OrderStateModificationMessage modification) {
        if(CodeProcessor.validateIdToken(modification.getUserId(), modification.getToken())) {
            Order order = orderService.getOrderByOrderId(modification.getOrderId());

            if(order == null)
                return new Result(Status.ERROR, "无法获取订单", null).toString();

            order.setState(modification.getState());

            Result result = OrderModule.updateOrder(orderService, order);

            return result.toString();
        }
        return new Result(Status.USER_ERROR, "id与token不一致", null).toString();
    }

    //取消订单
    @ResponseBody
    @PostMapping("/requests/user/cancelOrder")
    public String cancelSingleOrder(@RequestBody OrderFilter filter) {
        Result result = OrderModule.cancelOrder(orderService, filter);

        return result.toString();
    }
}
