package com.giovanny.study.design.singleton;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design
 * @className: Singleton1
 * @description: 单例模式-懒汉式  加锁  线程安全 synchronized 为独占排他锁，并发性能差。即使在创建成功以后，获取实例仍然是串行化操作。
 * @author: YangJun
 * @date: 2020/4/21 10:28
 * @version: v1.0
 **/
@Slf4j
@Data
public class Singleton3 {
    private static Singleton3 singleton = null;

    private Singleton3() {
    }

    private Integer id;
    private String name;

    public static synchronized Singleton3 getInstance() {
        if (singleton == null) {
            log.info("不存在，需要实例化");
            singleton = new Singleton3();
        } else {
            log.info("已经有了 inst=[{}]", singleton);
        }
        return singleton;
    }
}
