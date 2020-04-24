package com.giovanny.study.design.decorator;

public abstract class Decorator implements Drink {
    //装饰者
    //1、具有功能 实现接口
    //2、是抽象类
    //3、持有抽象接口的引用

    private Drink drink;

    //构造函数  覆盖默认的无参构造，表示只能装饰Drink
    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public double money() {
        return drink.money();
    }

    @Override
    public String desc() {
        return drink.desc();
    }
}
