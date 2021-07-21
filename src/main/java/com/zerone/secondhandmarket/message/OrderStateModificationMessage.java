package com.zerone.secondhandmarket.message;

import com.zerone.secondhandmarket.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStateModificationMessage {
    private Integer userId;
    private String token;
    private Integer orderId;
    private OrderState state;
}
