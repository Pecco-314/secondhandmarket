package com.zerone.secondhandmarket.entity;

import com.zerone.secondhandmarket.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Notification {
    private Integer releaser;
    private Integer target; //target == null => 广播(admin only); target != releaser => 单播(admin only); target == release => 个人信息
    //private NotificationType type;
    private String message;
    private String time;
}
