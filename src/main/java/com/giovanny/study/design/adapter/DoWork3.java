package com.giovanny.study.design.adapter;

/**
 * @packageName: com.giovanny.study.design.adapter
 * @className: DoWork3
 * @description: 业务场景3 接口的适配器模式
 * @author: YangJun
 * @date: 2020/4/30 19:14
 * @version: v1.0
 **/
public class DoWork3 extends AbstractAdapter {
    public void doSomething() {
        method1();
    }

    @Override
    public void method2() {
        super.method2();
    }
}
