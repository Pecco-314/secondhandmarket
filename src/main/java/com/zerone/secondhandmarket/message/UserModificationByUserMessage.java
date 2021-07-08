package com.zerone.secondhandmarket.message;
//普通用户修改个人信息时发送

import com.zerone.secondhandmarket.enums.UserHead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//update
@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class UserModificationByUserMessage {
    //locate
    private Integer userID;
    private String token;

    //update
    private String telephone;
    private String emailAddress;
    private String nickname;

}
