package com.zerone.secondhandmarket.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data               //生成get()、set()
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Notification {
    /**
     * 发布者
     */
    private String releaser;
    /**
     * 信息类别
     */
    private Integer type;
    /**
     * 发布时间
     */
    private Date time;
}
