package com.giovanny.study.decorator;

public class Coffee implements Drink {
    //被装饰者 实现Drink 具有某些功能

    @Override
    public double money() {
        return 10;
    }

    @Override
    public String desc() {
        return "咖啡";
    }
}
