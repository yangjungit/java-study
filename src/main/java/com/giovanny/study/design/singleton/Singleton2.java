package com.giovanny.study.design.singleton;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design.singleton
 * @className: Singleton2
 * @description: 饿汉式单例模式 线程安全
 * @author: YangJun
 * @date: 2020/4/21 11:13
 * @version: v1.0
 **/
@Slf4j
@Data
public class Singleton2 {
    private static Singleton2 singleton = new Singleton2();

    private Singleton2() {
        log.info("初始化。。。");
        this.id = 1;
        this.name = "name";
    }

    private Integer id;
    private String name;

    public static Singleton2 getSingleton() {
        return singleton;
    }


}
