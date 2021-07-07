package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("AdminOrder")
public class OrderController {
    @Autowired
    private OrderService orderService = new OrderService();

    public String getOrderList(@RequestBody OrderFilter filter) {
        Result result = OrderModule.getOrderList(orderService, filter);

        return JSONMapper.writeValueAsString(result);
    }
}
