package com.zerone.secondhandmarket.controller.User;

import com.zerone.secondhandmarket.entity.Order;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.message.OrderMessage;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("OrdinaryIndent")
public class OrderController {
    @Autowired
    private OrderService orderService = new OrderService();

//    @GetMapping("user/{userid}/history")
//    @ResponseBody
//    public ResultVo getHistoryIdent(@PathVariable int userid){
//        return null;
//    }

    @ResponseBody
    @PostMapping("/requests/orderList")
    public String getOrderList(@RequestBody int userId) {
        OrderFilter filter = new OrderFilter(userId, null, null);

        Result result = OrderModule.getOrderList(orderService, filter);

        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/insertOrder")
    public String generateSingleOrder(@RequestBody OrderMessage order) {
        Result result = OrderModule.generateOrder(orderService, order);

        return result.toString();
    }
}
