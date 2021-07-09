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
    private Integer id;
    private Integer buyer;
    private Integer seller;
    private Integer item;//商品id
    private Integer quantity;//数量
    private String time;//下单时间 字符串格式必须是"yyyy-MM-dd HH:mm:ss"
}
