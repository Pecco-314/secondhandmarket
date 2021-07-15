package com.zerone.secondhandmarket.entity;

import com.zerone.secondhandmarket.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Order {
    private Integer id;
    private Integer buyer;
    private Integer seller;
    private Integer item;//商品id
    private Integer quantity;//数量
    private String time;//下单时间 字符串格式必须是"yyyy-MM-dd HH:mm:ss
    private String receiverName;
    private String phoneNumber;
    private String campus;
    private String dorm;
    private String detailedAddress;
    private OrderState state;//订单状态 包括已完成和待收货
}
