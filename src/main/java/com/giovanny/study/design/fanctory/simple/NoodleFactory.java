package com.giovanny.study.design.fanctory.simple;

/**
 * @packageName: com.example.demo1.design.fanctory.simple
 * @className: NoodleFactory
 * @description: 工厂
 * @author: YangJun
 * @date: 2020/4/21 14:23
 * @version: v1.0
 **/
public class NoodleFactory {
    private static final String TYPE_1 = "noodle1";
    private static final String TYPE_2 = "noodle2";

    public Noodles getNoodles(String type) {
        switch (type) {
            case TYPE_1:
                return new Noodle1();
            case TYPE_2:
                return new Noodel2();
            default:
                return null;
        }

    }
}
