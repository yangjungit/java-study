package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sys_menu")
@Data
public class SysMenu {
    /**
     * ID
     */
    @Id
    @Column(name = "menu_id")
    private Long menuId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

}