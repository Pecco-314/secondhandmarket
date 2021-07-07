package com.zerone.secondhandmarket.entity;
import java.util.Date;

import com.zerone.secondhandmarket.message.OrderMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Order {
    private int id;
    private int buyer;
    private int seller;
    private int item;//商品id
    private int quantity;//数量
    private String time;//下单时间 字符串格式必须是"yyyy-MM-dd HH:mm:ss"
}
