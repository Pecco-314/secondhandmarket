package com.zerone.secondhandmarket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecondHandMarketController {

    @RequestMapping(value = "/")
    public String run() {
        return "index";
    }

}
