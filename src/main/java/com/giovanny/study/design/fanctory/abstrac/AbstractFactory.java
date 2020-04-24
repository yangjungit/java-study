package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: AbstractFactory
 * @description: //AbstractFactory
 * @author: YangJun
 * @date: 2020/4/21 14:59
 * @version: v1.0
 **/
public abstract class AbstractFactory {
    /**
     * 抽象方法获取形状
     *
     * @param shape 类型
     * @return 形状
     */
    public abstract Shape getShape(String shape);

    /**
     * 抽象方法获取颜色
     *
     * @param color 类型
     * @return 颜色
     */
    public abstract Color getColor(String color);
}
