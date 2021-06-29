package com.zerone.secondhandmarket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SecondHandMarketController {

    @RequestMapping(value="/helloWorld")
    public String helloWorld() {
        return "helloWorld";
    }

}
