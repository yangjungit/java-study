package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_user")
@Data
public class SysUser {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态 PROHIBIT：禁用   NORMAL：正常
     */
    private String status;

    /**
     * 盐值
     */
    private String salt;


}