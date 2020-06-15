package com.giovanny.study.config.runner;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.example.demo1.config.runner
 * @className: MyApplicationRunner
 * @description:
 * @author: YangJun
 * @date: 2020/4/22 9:41
 * @version: v1.0
 **/
@Component
@Slf4j
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("MyApplicationRunner args[{}]", args);
        log.info("现在spring容器都初始化完成了，这个时候做一些项目上的初始化配置等，如MySQL redis等数据准备。MyApplicationRunner...");
    }
}
