package com.giovanny.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @packageName: com.giovanny.study
 * @className: JavaStudyApplication
 * @description: 启动类  MapperScan 注意这个导入包是 tk.mybatis.spring.annotation.MapperScan
 * @author: YangJun
 * @date: 2020/4/24 12:10
 * @version: v1.0
 **/
@SpringBootApplication
@Slf4j
@MapperScan(basePackages = {"com.giovanny.study.mapper"})
@EnableScheduling
public class JavaStudyApplication {

    public static void main(String[] args) {
        try {
            System.setProperty("LOG_IP", InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            log.error(e.getMessage());
        }
        SpringApplication.run(JavaStudyApplication.class, args);
    }

}
