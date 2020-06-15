package com.giovanny.study.design.adapter;

/**
 * @packageName: com.giovanny.study.design.adapter
 * @className: DoWork1
 * @description: 业务场景1  类的适配器模式
 * @author: YangJun
 * @date: 2020/4/30 18:37
 * @version: v1.0
 **/
public class DoWork1 extends Compute implements FirstAdapter {

    /**
     * 继承 具有某种功能
     * 实现适配器方法  在方法内部调用功能
     */
    @Override
    public void firstAdapterMethod() {
        System.out.println("类的适配器模式开始，做一些转换等，以适配功能");
        computeData();
        System.out.println("类的适配器模式完成");
    }
}
