package com.giovanny.study.config.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @packageName: com.example.demo1.config.runner
 * @className: MyCommandLineRunner
 * @description:
 * @author: YangJun
 * @date: 2020/4/22 10:01
 * @version: v1.0
 **/
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("MyCommandLineRunner args:[{}]", Arrays.asList(args));
        log.info("现在spring容器都初始化完成了，这个时候做一些项目上命令行配置，MyCommandLineRunner...");

    }
}
