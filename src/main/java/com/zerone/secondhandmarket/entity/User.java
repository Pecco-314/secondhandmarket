package com.zerone.secondhandmarket.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class User {


    private int user_id;
    private String username;
    private String password;
    private String phone_number;
    private String email;
    private UserHead head=UserHead.HEAD0;//用户头像，取值为0,1,2，默认为0


    @Override
    public String toString() {
        return "User{" +
                "id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
