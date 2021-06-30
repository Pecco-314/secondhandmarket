package com.zerone.secondhandmarket.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lzl
 * @since 2021-06-30
 */

@Data               //生成get()、set()
@AllArgsConstructor //全参构造函数
@NoArgsConstructor  //无参构造函数
public class AdminInfo {
    /**
     * 管理员ID
     */
    private String adminID;
    /**
     * 管理员名
     */
    private String adminName;
    /**
     * 密码
     */
    private String adminPassword;
}
