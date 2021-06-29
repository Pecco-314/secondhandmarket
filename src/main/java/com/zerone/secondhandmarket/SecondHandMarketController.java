package com.zerone.secondhandmarket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SecondHandMarketController {

    @RequestMapping("/")
    public String run() {
        return "index";
    }

}
