package com.giovanny.study.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @packageName: com.example.demo1
 * @className: User
 * @description: User
 * @author: YangJun
 * @date: 2020/4/14 11:05
 * @version: v1.0
 **/
@Data
public class User implements Serializable {
    private String id;
    private String name;
    private String desc;

    public User() {
    }

    public User(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
