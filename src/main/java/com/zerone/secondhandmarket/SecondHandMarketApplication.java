package com.zerone.secondhandmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class SecondHandMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondHandMarketApplication.class, args);
    }

}