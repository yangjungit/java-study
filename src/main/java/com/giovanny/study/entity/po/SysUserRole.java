package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_user_role")
@Data
public class SysUserRole {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;


}