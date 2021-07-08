package com.zerone.secondhandmarket.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class PasswordModificationMessage {
    private Integer userID;
    private String token;

    private String oldPassword;
    private String newPassword;
}
