package com.giovanny.study.design.singleton;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design
 * @className: Singleton1
 * @description: 单例模式-懒汉式  双重校验
 * @author: YangJun
 * @date: 2020/4/21 10:28
 * @version: v1.0
 **/
@Slf4j
@Data
public class Singleton4 {
    private static Singleton4 singleton = null;

    private Singleton4() {
    }

    private Integer id;
    private String name;

    public static Singleton4 getInstance() {
        if (singleton == null) {
            synchronized (Singleton4.class) {
                if (singleton == null) {
                    log.info("不存在，需要实例化");
                    singleton = new Singleton4();
                }
            }
        } else {
            log.info("已经有了 inst=[{}]", singleton);
        }
        return singleton;
    }
}
