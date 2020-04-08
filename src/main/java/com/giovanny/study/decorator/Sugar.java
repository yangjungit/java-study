package com.giovanny.study.decorator;

public class Sugar extends Decorator {
    //装饰的的东西   给被装饰的对象装饰的东西
    //继承装饰器 具有装饰的功能

    //重写父类的装饰方法
    public Sugar(Drink drink) {
        super(drink);
    }

    @Override
    public double money() {
        return super.money() + 2;
    }

    @Override
    public String desc() {
        return super.desc() + "加糖";
    }
}
