package com.giovanny.study.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @packageName: com.example.demo1.controller
 * @className: TestController
 * @description: TestController
 * @author: YangJun
 * @date: 2020/4/8 17:49
 * @version: v1.0
 **/
@Slf4j
@RestController
public class TestController {

    @GetMapping(value = "/testMethod1")
    public int testMethod1(String str1, String str2){
        log.info("param:{},{}",str1,str2);
        return 1;
    }
}
