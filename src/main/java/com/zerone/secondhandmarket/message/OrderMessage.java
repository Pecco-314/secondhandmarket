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
    //locate
    private Integer buyer;
    private Integer seller;
    private Integer itemID;

    //update
    private Integer quantity;
}
