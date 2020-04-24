package com.giovanny.study.design.fanctory.abstrac;

/**
 * @packageName: com.example.demo1.design.fanctory.abstrac
 * @className: FactoryProducer
 * @description:
 * @author: YangJun
 * @date: 2020/4/21 15:16
 * @version: v1.0
 **/
public class FactoryProducer {
    public static AbstractFactory getFactory(String factory) {
        switch (factory) {
            case "shape":
                return new ShapeFactory();
            case "color":
                return new ColorFactory();
            default:
                return null;
        }

    }
}
