package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: Cycle
 * @description: 画一个圆
 * @author: YangJun
 * @date: 2020/4/21 14:55
 * @version: v1.0
 **/
public class Cycle implements Shape {
    @Override
    public void draw() {
        System.out.println("画一个圆...");
    }
}
