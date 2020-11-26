package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_role_menu")
@Data
public class SysRoleMenu {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "menu_id")
    private Long menuId;


}