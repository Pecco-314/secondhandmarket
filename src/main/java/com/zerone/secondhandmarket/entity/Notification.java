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
    private NotificationType type;
    private Date time;
}
