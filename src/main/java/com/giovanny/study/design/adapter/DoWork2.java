package com.giovanny.study.design.adapter;

/**
 * @packageName: com.giovanny.study.design.adapter
 * @className: DoWork2
 * @description: 业务场景2  对象的适配器模式
 * @author: YangJun
 * @date: 2020/4/30 18:37
 * @version: v1.0
 **/
public class DoWork2 implements SecondAdapter {

    private Compute compute;

    /**
     * 覆盖无参构造方法
     *
     * @param compute 功能
     */
    public DoWork2(Compute compute) {
        this.compute = compute;
    }

    /**
     * 持有某种某种功能
     * 实现适配器方法  在方法内部调用功能
     */
    @Override
    public void secondAdapterMethod() {
        if (compute != null) {
            System.out.println("对象的适配器模式开始，做一些转换等，以适配功能");
            compute.computeData();
            System.out.println("对象的适配器模式完成");
        }
    }
}
