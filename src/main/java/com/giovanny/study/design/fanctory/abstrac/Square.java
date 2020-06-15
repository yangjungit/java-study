package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: Square
 * @description: 矩形
 * @author: YangJun
 * @date: 2020/4/21 14:53
 * @version: v1.0
 **/
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("画一个矩形。。。");
    }
}
