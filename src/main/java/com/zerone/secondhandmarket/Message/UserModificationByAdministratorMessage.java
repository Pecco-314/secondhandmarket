package com.zerone.secondhandmarket.Message;
//管理员修改某用户信息时发送


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//update
@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class UserModificationByAdministratorMessage {
    //locate
    private Integer userID;

    //update
    private String nickName;
}
