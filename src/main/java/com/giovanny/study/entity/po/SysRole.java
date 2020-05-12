package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_role")
@Data
public class SysRole {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;


}