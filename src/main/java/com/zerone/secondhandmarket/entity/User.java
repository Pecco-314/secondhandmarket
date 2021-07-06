package com.zerone.secondhandmarket.entity;

import com.zerone.secondhandmarket.enums.UserHead;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class User {
    private int id;
    private String nickname;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    private String imagePath;//用户头像图片路径


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phoneNumber + '\'' +
                ", email='" + emailAddress + '\'' +
                '}';
    }

}
