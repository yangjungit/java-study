package com.giovanny.study.config.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @packageName: com.example.demo1.config.runner
 * @className: MyInitial
 * @description: MyApplicationContextInitializer 需要在classpath下 META-INF/spring.factories 里面配置类
 * @author: YangJun
 * @date: 2020/4/22 10:04
 * @version: v1.0
 **/
@Slf4j
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        // configurableApplicationContext.getBean("")
        log.info("这里可以开始对硬件、网络等进行检测，检测到不符合项目启动要求就不启动了，MyApplicationContextInitializer...");
    }
}
