package com.giovanny.study.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @packageName: com.example.demo1.design.proxy
 * @className: ProxyHandle
 * @description: 动态代理
 * @author: YangJun
 * @date: 2020/4/21 16:16
 * @version: v1.0
 **/
public class ProxyHandle implements InvocationHandler {
    private Image image;

    public ProxyHandle(Image image) {
        this.image = image;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object invoke = method.invoke(image, args);
        System.out.println("after invoke. invoke=" + invoke);
        return invoke;
    }
}
