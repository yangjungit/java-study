package com.giovanny.study.utils;

public class TestDemo1 {
    public static void main(String[] args) {
        Thread threadAa = new Thread(new Runnable() {
            @Override
            public void run() {
                doSomething("A");
            }
        });

        Thread threadBb = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    threadAa.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doSomething("B");
            }
        });

        threadAa.start();
        threadBb.start();

    }

    public static void doSomething(String name) {
        int i = 0;
        while (i++ < 3) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("name=" + name + ":" + i);
        }
    }
}
