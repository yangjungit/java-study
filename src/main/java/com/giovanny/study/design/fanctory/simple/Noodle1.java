package com.giovanny.study.design.fanctory.simple;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design.fanctory.simple
 * @className: Noode1
 * @description: 第一种面
 * @author: YangJun
 * @date: 2020/4/21 12:10
 * @version: v1.0
 **/
@Data
@Slf4j
public class Noodle1 implements Noodles {
    private String color;

    @Override
    public Integer price() {
        return 1;
    }

    @Override
    public void how() {
        this.color = "红色";
    }

    @Override
    public void describe() {
        log.info("noodle1...");
    }
}
