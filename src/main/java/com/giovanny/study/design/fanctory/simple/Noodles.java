package com.giovanny.study.design.fanctory.simple;

/**
 * @packageName: com.example.demo1.design.fanctory.simple
 * @className: Noodles
 * @description: 基本的产品
 * @author: YangJun
 * @date: 2020/4/21 12:06
 * @version: v1.0
 **/
public interface Noodles {
    /**
     * 定价
     *
     * @return 价格
     */
    Integer price();

    /**
     * 怎么做
     */
    void how();

    /**
     * 描述
     */
    void describe();

}
