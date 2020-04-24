package com.giovanny.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.giovanny.study.entity.User;
import com.giovanny.study.rabbitmq.RabbitMsgProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private RabbitMsgProducer rabbitMsgProducer;

    @RequestMapping("/rabbit/hello")
    public String sendRabbitMqMsg() {
        User user = new User("sd11", "h哈", "12.sd和");
        rabbitMsgProducer.convertAndSend("directQueue", user);
        return "success";
    }
}
