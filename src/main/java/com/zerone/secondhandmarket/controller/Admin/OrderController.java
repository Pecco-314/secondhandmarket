package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("AdminOrder")
public class OrderController {
    @Autowired
    private OrderService orderService = new OrderService();
    @Autowired
    private ItemService itemService = new ItemService();

    @ResponseBody
    @PostMapping("/requests/admin/order/{userId}")
    public String getOrderList(@PathVariable int userId) {
        Result result = OrderModule.getOrderList(orderService, itemService, userId);

        return JSONMapper.writeValueAsString(result);
    }
}
