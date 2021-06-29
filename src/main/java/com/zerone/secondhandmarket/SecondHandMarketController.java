package com.zerone.secondhandmarket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecondHandMarketController {

    @RequestMapping("/")
    public String run() {
        return "index";
    }

}
