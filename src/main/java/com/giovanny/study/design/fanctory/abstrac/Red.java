package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: Red
 * @description:
 * @author: YangJun
 * @date: 2020/4/21 14:58
 * @version: v1.0
 **/
public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("染一个红色。。。");
    }
}
