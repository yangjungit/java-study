package com.giovanny.study.streamstudy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @packageName: com.example.demo1.streamstudy
 * @className: StreamDemo1
 * @description: java流
 * @author: YangJun
 * @date: 2020/4/9 15:52
 * @version: v1.0
 **/
public class StreamDemo1 {
    public static List<String> createList(int size) {
        List<String> list = new ArrayList<>();
        char ch = 'A';
        for (int i = 0; list.size() < size; i++) {
            list.add(i + 1 + "");
            if (ch <= 'Z') {
                String str = String.valueOf(ch);
                list.add(str);
            } else {
                ch = 'A';
            }
            ch = (char) (ch + 1);
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> list = createList(10);
        list.forEach(str-> System.out.print(str+""));
        List<String> filterList = list
                .stream()
                .filter(str->!"A".equals(str))
                .sorted(String::compareTo)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        System.out.println("");
        filterList.forEach(str-> System.out.print(str+""));

    }

}
