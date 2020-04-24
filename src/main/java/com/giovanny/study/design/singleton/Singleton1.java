package com.giovanny.study.design.singleton;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design
 * @className: Singleton1
 * @description: 单例模式-懒汉式  优点：懒加载，启动快，资源占用小，使用时才实例化，无锁。缺点：非线程安全
 * @author: YangJun
 * @date: 2020/4/21 10:28
 * @version: v1.0
 **/
@Slf4j
@Data
public class Singleton1 {
    private static Singleton1 singleton = null;

    private Singleton1() {
    }

    private Integer id;
    private String name;

    public static Singleton1 getInstance() {
        if (singleton == null) {
            log.info("不存在，需要实例化");
            singleton = new Singleton1();
        } else {
            log.info("已经有了 inst=[{}]", singleton);
        }
        return singleton;
    }
}
