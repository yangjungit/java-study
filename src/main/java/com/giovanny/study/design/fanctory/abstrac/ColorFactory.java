package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: ColorFactory
 * @description: 颜色工厂
 * @author: YangJun
 * @date: 2020/4/21 15:11
 * @version: v1.0
 **/
public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        return null;
    }

    @Override
    public Color getColor(String color) {
        switch (color) {
            case "white":
                return new White();
            case "red":
                return new Red();
            default:
                return null;
        }
    }
}
