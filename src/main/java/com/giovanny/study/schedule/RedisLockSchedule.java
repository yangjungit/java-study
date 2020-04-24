package com.giovanny.study.schedule;

import com.giovanny.study.annotation.RedisLockAnnotation;
import com.giovanny.study.rabbitmq.RabbitMsgProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @packageName: com.giovanny.study.schedule
 * @className: RedisLockSchedule
 * @description: redis实现分布式锁   用于多实例执行不可重复的定时任务
 * @author: YangJun
 * @date: 2020/4/24 17:24
 * @version: v1.0
 **/
@Component
@Slf4j
public class RedisLockSchedule {
    @Autowired
    private RabbitMsgProducer rabbitMsgProducer;
    @Value("spring.rabbitmq.queue-name")
    private String queueName;

    @Scheduled(cron = "0 0/1 * * * ?")
    @RedisLockAnnotation(holdTimeMillis = 2 * 1000 * 60)
    public void redisLockTest() {
        log.info("定时任务执行。。。。。。");
        rabbitMsgProducer.convertAndSend(queueName, "lalalala");
    }
}
