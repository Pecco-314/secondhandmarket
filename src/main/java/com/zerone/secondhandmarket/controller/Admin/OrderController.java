package com.zerone.secondhandmarket.controller.Admin;

import com.zerone.secondhandmarket.enums.Status;
import com.zerone.secondhandmarket.message.AdminTokenMessage;
import com.zerone.secondhandmarket.message.OrderFilter;
import com.zerone.secondhandmarket.module.OrderModule;
import com.zerone.secondhandmarket.service.ItemService;
import com.zerone.secondhandmarket.service.OrderService;
import com.zerone.secondhandmarket.tools.CodeProcessor;
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
        String res = Router.routerForAdmin(request, "tables-order");
        //System.out.println(res);
        return res;
    }

    @ResponseBody
    @PostMapping("/requests/admin/order")
    public String getAllOrders(@RequestBody AdminTokenMessage token) {
        Result result;
        if (CodeProcessor.validateIdToken(token.getAdminID(), token.getToken())) {
            OrderFilter orderFilter = new OrderFilter();
            result = OrderModule.getOrderListByFilter(orderService, orderFilter);
        } else {
            result = new Result(Status.ORDER_ERROR, "ID与Token不符", null);
        }
        return result.toString();
    }

    @ResponseBody
    @PostMapping("/requests/admin/order/{userId}")
    public String getOrderList(@PathVariable int userId) {
        Result result = OrderModule.getOrderList(orderService, userId, true);

        return JSONMapper.writeValueAsString(result);
    }
}
