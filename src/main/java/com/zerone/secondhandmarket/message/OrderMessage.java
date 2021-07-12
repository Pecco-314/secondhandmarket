package com.zerone.secondhandmarket.message;
//用户下单时发送的信息

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
//update or insert
public class OrderMessage {
    private Integer buyer;
    private Integer seller;

    //locate
    private String receiverName;
    private String phoneNumber;
    private String campus;
    private String dorm;
    private String detailedAddress;
    private Integer itemID;

    //update
    private Integer quantity;
}
