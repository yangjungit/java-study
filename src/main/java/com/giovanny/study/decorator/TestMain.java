package com.giovanny.study.decorator;

public class TestMain {
    public static void main(String[] args) {
        Drink coffee = new Coffee();
        System.out.println(coffee.money());
        System.out.println(coffee.desc());
        System.out.println("------------");
        Drink coffeeSugar = new Sugar(coffee);
        System.out.println(coffeeSugar.money());
        System.out.println(coffeeSugar.desc());
        System.out.println("------------");
    }
}
