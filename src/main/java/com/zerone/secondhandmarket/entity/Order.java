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
    private int order_id;
    private int buyer_id;
    private int seller_id;
    private int item_id;//商品id
    private int quantity;//数量
    private String ordering_time;//下单时间 字符串格式必须是"YYYY-MM-DD HH:MM:SS"

    public Order(OrderMessage message) {
        buyer_id = message.getBuyer();
        seller_id = message.getSeller();
        item_id = message.getItemID();
        quantity = message.getQuantity();
    }
}
