package com.zerone.secondhandmarket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistModificationMessage {
    private Integer userID;
    private String token;
    private Integer itemID;

    private Boolean isAdding;
}
