package com.giovanny.study.entity.po;

import lombok.Data;

import javax.persistence.*;

/**
 * @packageName: com.example.demo1.entity.po
 * @className: Uuu
 * @description: 测试
 * @author: YangJun
 * @date: 2020/4/15 8:52
 * @version: v1.0
 **/
@Data
public class Uuu {
    /**
     * 自增主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 获取自增主键id
     *
     * @return id - 自增主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键id
     *
     * @param id 自增主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}