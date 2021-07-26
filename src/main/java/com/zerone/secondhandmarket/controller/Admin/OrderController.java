package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.JSONMapper;
import com.zerone.secondhandmarket.tools.Router;
import com.zerone.secondhandmarket.viewobject.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller("AdminOrder")
public class OrderController {
    @Autowired
    private OrderService orderService = new OrderService();
    @Autowired
    private ItemService itemService = new ItemService();

    @RequestMapping("/admin-order")
    public String openAdminOrderPage(HttpServletRequest request) {
        return Router.routerForAdmin(request, "tables-order");
    }

    @ResponseBody
    @GetMapping("/requests/admin/order")
    public String getAllOrders() {
        OrderFilter orderFilter = new OrderFilter();
        Result result = OrderModule.getOrderListByFilter(orderService, orderFilter);

        return JSONMapper.writeValueAsString(result);
    }

    @ResponseBody
    @PostMapping("/requests/admin/order/{userId}")
    public String getOrderList(@PathVariable int userId) {
        Result result = OrderModule.getOrderList(orderService, userId, true);

        return JSONMapper.writeValueAsString(result);
    }
}
