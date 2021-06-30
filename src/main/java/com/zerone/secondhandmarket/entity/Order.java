package com.zerone.secondhandmarket.entity;
import java.util.Date;
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
    private Date ordering_time;//下单时间


}
