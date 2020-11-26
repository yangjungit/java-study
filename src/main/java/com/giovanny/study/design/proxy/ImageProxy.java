package com.giovanny.study.design.proxy;

/**
 * @packageName: com.example.demo1.design.proxy
 * @className: ImageProxy
 * @description:
 * @author: YangJun
 * @date: 2020/4/21 15:56
 * @version: v1.0
 **/
public class ImageProxy implements Image {
    private RealImage realImage;
    private String fileName;

    public ImageProxy(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String display() {
        if (realImage == null) {
            System.out.println("需要加载...");
            realImage = new RealImage(fileName);
        }
        realImage.display();
        return "success";
    }
}
