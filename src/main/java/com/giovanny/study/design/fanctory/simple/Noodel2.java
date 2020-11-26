package com.giovanny.study.design.fanctory.simple;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @packageName: com.example.demo1.design.fanctory.simple
 * @className: noodel2
 * @description: //TODO
 * @author: YangJun
 * @date: 2020/4/21 14:21
 * @version: v1.0
 **/
@Slf4j
@Data
public class Noodel2 implements Noodles {
    private String color;

    @Override
    public Integer price() {
        return 2;
    }

    @Override
    public void how() {
        this.color = "白色";
    }

    @Override
    public void describe() {
        log.info("noodle2...");
    }
}
