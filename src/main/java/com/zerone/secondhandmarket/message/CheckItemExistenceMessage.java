package com.zerone.secondhandmarket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckItemExistenceMessage {
    private Integer userId;
    private String token;
    private Integer itemId;
}
