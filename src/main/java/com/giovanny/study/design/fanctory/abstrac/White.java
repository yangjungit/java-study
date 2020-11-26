package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: White
 * @description: 白色
 * @author: YangJun
 * @date: 2020/4/21 14:57
 * @version: v1.0
 **/
public class White implements Color {
    @Override
    public void fill() {
        System.out.println("染一个白色。。。");
    }
}
