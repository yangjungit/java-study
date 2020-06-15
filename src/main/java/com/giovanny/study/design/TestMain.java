package com.giovanny.study.design;


import com.giovanny.study.design.proxy.Image;
import com.giovanny.study.design.proxy.ProxyHandle;
import com.giovanny.study.design.proxy.RealImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @packageName: com.example.demo1.design
 * @className: TestMain
 * @description: tests
 * @author: YangJun
 * @date: 2020/4/21 10:37
 * @version: v1.0
 **/
@Slf4j
@ConditionalOnProperty
public class TestMain {
    public static void main(String[] args) {
        Image realImage = new RealImage("ha");
        InvocationHandler proxyHandle = new ProxyHandle(realImage);
        Image image = (Image) Proxy.newProxyInstance(realImage.getClass().getClassLoader(), realImage.getClass().getInterfaces(), proxyHandle);
        image.display();

    }
}
