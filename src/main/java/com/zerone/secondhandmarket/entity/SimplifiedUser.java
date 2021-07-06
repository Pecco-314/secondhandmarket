package com.zerone.secondhandmarket.entity;

import com.zerone.secondhandmarket.enums.UserHead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class SimplifiedUser {
    private int id;
    private String nickname;
    private String image;//用户头像
}
