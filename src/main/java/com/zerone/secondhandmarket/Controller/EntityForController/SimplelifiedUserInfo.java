package com.zerone.secondhandmarket.Controller.EntityForController;

import com.zerone.secondhandmarket.entity.UserHead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class SimplelifiedUserInfo {
    private Integer userId;
    private String username;
    private UserHead userHead;
}
