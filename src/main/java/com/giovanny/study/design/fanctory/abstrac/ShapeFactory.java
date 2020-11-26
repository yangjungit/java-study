package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: ShapeFactory
 * @description: 形状工厂
 * @author: YangJun
 * @date: 2020/4/21 15:05
 * @version: v1.0
 **/
public class ShapeFactory extends AbstractFactory {
    @Override
    public Shape getShape(String shape) {
        switch (shape) {
            case "square":
                return new Square();
            case "cycle":
                return new Cycle();
            default:
                return null;
        }
    }

    @Override
    public Color getColor(String color) {
        return null;
    }
}
