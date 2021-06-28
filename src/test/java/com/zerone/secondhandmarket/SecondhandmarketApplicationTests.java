package com.zerone.secondhandmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SecondhandmarketApplicationTests {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World， Spring boot is good";
    }

    public static void main(String[] args) {
        SpringApplication.run(SecondhandmarketApplicationTests.class, args);
    }


}