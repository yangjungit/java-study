package com.giovanny.study.controller;

import com.giovanny.study.entity.MyResponse;
import com.giovanny.study.entity.User;
import com.giovanny.study.entity.po.Uuu;
import com.giovanny.study.rabbitmq.RabbitMsgProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @packageName: com.example.demo1.controller
 * @className: RabbitMqController
 * @description: Rabbit mq 测试controller
 * @author: YangJun
 * @date: 2020/4/13 14:35
 * @version: v1.0
 **/
@Slf4j
@RestController
public class RabbitMqController {

    @Autowired
    @Qualifier("executor")
    private Executor executor;

    @Autowired
    private RabbitMsgProducer rabbitMsgProducer;


    @RequestMapping("/rabbit/hello")
    public String sendRabbitMqMsg() {
        User user = new User("sd11", "h哈", "12.sd和");
        rabbitMsgProducer.convertAndSend("directQueue", user);
        return "success";
    }

    /**
     * 发送大量数据到rabbitmq队列中，存入MySQL  MySQL性能很差，2000条消息消费完需要15-25秒
     * 顺便插播一个kafka的文章
     * https://blog.csdn.net/Pursue_success/article/details/86752317?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1
     *
     * @return MyResponse
     */
    @GetMapping("/rabbit/concurrency")
    public MyResponse sendMqMsgBatch() {
        executor.execute(() -> {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 200; j++) {
                    Uuu uuu = new Uuu();
                    uuu.setName("name-" + i + "-" + j);
                    uuu.setDescription("desc");
                    rabbitMsgProducer.convertAndSend("directQueue", uuu);
                }
            }
        });
        return MyResponse.success();
    }

    @GetMapping("/rabbit/one")
    public MyResponse sendMqMsg() {
        Uuu uuu = new Uuu();
        uuu.setName("name-");
        uuu.setDescription("desc");
        log.info("uuu:[{}]",uuu);
        rabbitMsgProducer.convertAndSend("directQueue", uuu);
        uuu.setName("haha");
        rabbitMsgProducer.convertAndSend("directQueue", uuu);
        return MyResponse.success();
    }
}
