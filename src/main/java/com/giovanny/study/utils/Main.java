package com.giovanny.study.utils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        input.close();
        int total = num;
        while (num >= 3) {
            int dui = num / 3;
            total += dui;
            int yu = num % 3;
            num = dui + yu;
        }
        System.out.println(total);
    }
}


