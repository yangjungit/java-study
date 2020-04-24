package com.giovanny.study.config.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @packageName: com.example.demo1.config.runner
 * @className: MyListener
 * @description: 需要在classpath下 META-INF/spring.factories 里面配置类
 * @author: YangJun
 * @date: 2020/4/22 14:28
 * @version: v1.0
 **/
@Slf4j
public class MySpringApplicationRunListener implements SpringApplicationRunListener {

    public MySpringApplicationRunListener(SpringApplication application, String[] args) {
    }

    @Override
    public void starting() {
        log.info("---------starting 项目开始启动。。。。。");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("---------environmentPrepared  环境变量准备开始");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("---------contextPrepared  上下文准备开始");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("---------contextLoaded  上下文加载开始");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("---------started  上下文加载完成");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("---------running  项目已启动");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.error("---------failed  项目启动失败");
    }
}
