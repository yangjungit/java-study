package com.giovanny.study.design.proxy;

/**
 * @packageName: com.example.demo1.design.proxy
 * @className: RealImage
 * @description:
 * @author: YangJun
 * @date: 2020/4/21 15:52
 * @version: v1.0
 **/
public class RealImage implements Image {
    private String fileName;

    @Override
    public String display() {
        System.out.println("image:" + fileName);
        return "success";
    }

    public RealImage(String fileName) {
        this.fileName = fileName;
    }

    public RealImage() {
    }
}
