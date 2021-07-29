package com.zerone.secondhandmarket;

import com.zerone.secondhandmarket.tools.Router;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecondHandMarketController {
    @RequestMapping("/")
    public String run(HttpServletRequest request) {
        String res = Router.routerForUserAndVistor(request, "index");
        System.out.println(res);
        return res;
    }

}
